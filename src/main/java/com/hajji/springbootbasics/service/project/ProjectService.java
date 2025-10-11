package com.hajji.springbootbasics.service.project;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.project.CreateProjectRequestDTO;
import com.hajji.springbootbasics.dto.project.ProjectResponseDTO;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.project.AccessDeniedException;
import com.hajji.springbootbasics.exceptions.project.DuplicateAssignmentException;
import com.hajji.springbootbasics.mapper.FileStorageMapper;
import com.hajji.springbootbasics.mapper.ProjectMapper;
import com.hajji.springbootbasics.model.*;
import com.hajji.springbootbasics.repository.*;
import com.hajji.springbootbasics.service.*;
import com.hajji.springbootbasics.service.role.contracts.RoleProjectChecker;
import com.hajji.springbootbasics.service.role.RoleService;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService  {

    private final ProjectRepository projectRepository;
    private final ProjectAssignmentRepository projectAssignmentRepository;
    private final ProjectDocumentRepository projectDocumentRepository;
    private final DocumentRevisionRepository documentRevisionRepository;
    private final CustomerService customerService;
    private final StandardService standardService;
    private final StatusService statusService;
    private final UserService userService;
    private final RoleService roleService;
    private final FileStorageService fileStorageService;

    public ProjectService(ProjectRepository projectRepository, ProjectAssignmentRepository projectAssignmentRepository, ProjectDocumentRepository projectDocumentRepository, DocumentRevisionRepository documentRevisionRepository, CustomerService customerService, StandardService standardService, StatusService statusService, UserService userService, RoleService roleService, FileStorageService fileStorageService) {
        this.projectRepository = projectRepository;
        this.projectAssignmentRepository = projectAssignmentRepository;
        this.projectDocumentRepository = projectDocumentRepository;
        this.documentRevisionRepository = documentRevisionRepository;
        this.customerService = customerService;
        this.standardService = standardService;
        this.statusService = statusService;
        this.userService = userService;
        this.roleService = roleService;
        this.fileStorageService = fileStorageService;
    }




    @Transactional
    public ProjectResponseDTO createProject(CreateProjectRequestDTO dto) {

        // fetch customer who will own this project !!
        Customer customer = customerService.findCustomerById(dto.getCustomerId());

        // fetch which standard will this project be about
        Standard standard = standardService.findStandardById(dto.getStandardId());

        // here we need to fetch draft status of prjoect because this is a new project
        ProjectStatus plannedProjectStatus = statusService.findProjectStatusByName("planned");


        // Create the Project
        Project project = new Project();
        project.setCustomer(customer);
        project.setStandard(standard);
        project.setStatus(plannedProjectStatus);
        project.setStartDate(LocalDateTime.now());
        project.setCompletionDate(null);
        project.setCreatedAt(LocalDateTime.now());
        project.setModifiedAt(LocalDateTime.now());
        project = projectRepository.save(project);



        // Clone StandardTemplates to ProjectDocuments
        Set<StandardTemplate> templates = standardService.getTemplatesByStandardId(dto.getStandardId());
        DocumentStatus draftDocumentStatus = statusService.findDocumentStatusByName("draft");
        Set<ProjectDocument>  projectDocuments = new HashSet<>();
        for (StandardTemplate template : templates) {
            ProjectDocument doc = new ProjectDocument();
            doc.setProject(project);
            doc.setTemplate(template);
            doc.setFile(template.getFile());
            doc.setStatus(draftDocumentStatus);
            doc.setVersionNumber(1);
            doc.setCreatedAt(LocalDateTime.now());
            doc.setModifiedAt(LocalDateTime.now());
            projectDocuments.add(doc);
            projectDocumentRepository.save(doc);
        }
        project.setProjectDocuments(projectDocuments);
        return ProjectMapper.toDTO(project);
    }



    @Transactional
    public void deleteProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // delete project roles
        projectAssignmentRepository.findByProject_ProjectId(projectId).forEach(assignment -> {
            assignment.getRoles().clear();
            projectAssignmentRepository.save(assignment);
        });

        // delete project assignments
        projectAssignmentRepository.deleteByProject_ProjectId(projectId);

        // delete document revisions
        projectDocumentRepository.findByProject_ProjectId(projectId).forEach(doc -> {
            documentRevisionRepository.deleteByProjectDocument_ProjectDocumentId(doc.getProjectDocumentId());
        });

        // delete project documents
        projectDocumentRepository.deleteByProject_ProjectId(projectId);

        // finally delete project
        projectRepository.delete(project);
    }



    @Transactional
    public void assignUserWithRole(Integer projectId, Integer userId, Integer roleId) {
        // fetch the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        // fetch the user
        User user = userService.getUserById(userId);

        // fetch the role
        Role role = roleService.getRoleById(roleId);

        // find or create assignment
        ProjectAssignment projectAssignment = projectAssignmentRepository
                .findByProject_ProjectIdAndUser_UserId(projectId, userId)
                .orElseGet(() -> {
                    ProjectAssignment pa = new ProjectAssignment();
                    pa.setProject(project);
                    pa.setUser(user);
                    pa.setAssignedAt(LocalDateTime.now());
                    return pa;
                });

        // prevent duplicate roles
        if (projectAssignment.getRoles().contains(role)) {
            throw new DuplicateAssignmentException(
                    String.format("User %d already has role %s in project %d", userId, role.getRoleName(), projectId)
            );
        }

        // assign new role
        projectAssignment.getRoles().add(role);
        projectAssignmentRepository.save(projectAssignment);
    }



    @Transactional
    public void uploadNewVersion(int projectId,
                                 int projectDocumentId,
                                 MultipartFile file,
                                 int customerId,
                                 String changeNote) {


        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        if (!project.getCustomer().getCustomerId().equals(customerId)) {
            throw new AccessDeniedException("Customer not authorized for this project");
        }


        // 1️⃣ Validate project + document relationship
        ProjectDocument document = projectDocumentRepository.findById(projectDocumentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));


        if (document.getProject().getProjectId() != projectId) {
            throw new ValidationException("Document does not belong to specified project");
        }

        // 2️⃣ Save uploaded file in FileStorage
        FileStorageResponseDTO fileStorageResponseDTO = fileStorageService.uploadFile(file,null,customerId);

        // 3️⃣ Determine next version
        int nextVersion = documentRevisionRepository
                .findMaxVersionByProjectDocumentId(projectDocumentId)
                .orElse(1);

        // 4️⃣ Record new revision (delegated helper)
        recordDocumentRevision(document, fileStorageResponseDTO, nextVersion + 1, changeNote);

        // 5️⃣ Update ProjectDocument
        FileStorage storedFile = FileStorageMapper.toEntity(fileStorageResponseDTO);
        document.setFile(storedFile);
        document.setVersionNumber(nextVersion + 1);
        document.setModifiedAt(LocalDateTime.now());

        projectDocumentRepository.save(document);
    }

    /**
     * 🧩 Helper: Handles creation of a new DocumentRevision.
     */
    private void recordDocumentRevision(ProjectDocument document,
                                        FileStorageResponseDTO fileStorageResponseDTO,
                                        int newVersion,
                                        String changeNote) {

        DocumentRevision revision = new DocumentRevision();
        revision.setProjectDocument(document);
        revision.setFile(FileStorageMapper.toEntity(fileStorageResponseDTO));
        revision.setVersionNumber(newVersion);
        revision.setChangeNote(changeNote);
        revision.setModifiedAt(LocalDateTime.now());
        revision.setCreatedAt(LocalDateTime.now());

        documentRevisionRepository.save(revision);
    }





}
