package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.standard.*;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.fileStorage.InvalidFileTypeException;
import com.hajji.springbootbasics.mapper.StandardMapper;
import com.hajji.springbootbasics.mapper.StandardSectionMapper;
import com.hajji.springbootbasics.model.*;
import com.hajji.springbootbasics.repository.*;
import com.hajji.springbootbasics.utility.PaginationLogger;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StandardService {

    private final StandardRepository standardRepository;
    private final StandardSectionRepository standardSectionRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final StandardTemplateRepository standardTemplateRepository;
    private final ProjectRepository projectRepository;
    private final ProjectDocumentRepository projectDocumentRepository;
    private final DocumentRevisionRepository documentRevisionRepository;

    public StandardService(StandardRepository standardRepository, StandardSectionRepository standardSectionRepository, UserService userService, FileStorageService fileStorageService, StandardTemplateRepository standardTemplateRepository, ProjectRepository projectRepository, ProjectDocumentRepository projectDocumentRepository, DocumentRevisionRepository documentRevisionRepository) {
        this.standardRepository = standardRepository;
        this.standardSectionRepository = standardSectionRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.standardTemplateRepository = standardTemplateRepository;
        this.projectRepository = projectRepository;
        this.projectDocumentRepository = projectDocumentRepository;
        this.documentRevisionRepository = documentRevisionRepository;
    }


    /* ---------------- STANDARD CRUD ---------------- */

    @Transactional
    public StandardResponseDTO createStandard(CreateStandardRequestDTO dto) {
        if (standardRepository.existsByNameAndVersion(dto.getName(), dto.getVersion())) {
            throw new IllegalStateException(
                    "Standard with name '" + dto.getName() + "' and version '" + dto.getVersion() + "' already exists"
            );
        }

        Standard standard = StandardMapper.toEntity(dto);
        standard = standardRepository.save(standard);

        return StandardMapper.toResponseDTO(standard);
    }



    @Transactional
    public StandardResponseDTO updateStandard(UpdateStandardRequestDTO dto) {
        if (!dto.getStandardId().isProvided()) {
            throw new IllegalArgumentException("Standard ID is required for update");
        }

        Standard standard = standardRepository.findById(dto.getStandardId().get())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Standard not found with ID " + dto.getStandardId().get()
                ));

        // Patch updates using ifProvided
        dto.getName().ifProvided(standard::setName);
        dto.getVersion().ifProvided(standard::setVersion);

        // Update modification timestamp
        standard.setModifiedAt(LocalDateTime.now());

        Standard updated = standardRepository.save(standard);
        return StandardMapper.toResponseDTO(updated);  // assuming you have a mapper similar to UserMapper
    }


    @Transactional
    public List<StandardResponseDTO> getAllStandards(Pageable pageable) {

        // Log page fetch
        PaginationLogger.logPageFetch("Standards", pageable);

        // Fetch all standards
        return standardRepository.findAll(pageable) // <-- using pageable
                .stream()
                .map(StandardMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public StandardResponseDTO getStandardById(Integer id) {
        Standard standard = standardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found with ID: " + id));

        return StandardMapper.toResponseDTO(standard);
    }

    @Transactional
    public void deleteStandard(Integer id) {
        Standard standard = standardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found with ID: " + id));

        standardRepository.delete(standard);
    }

    /* ---------------- SECTION CREATE ---------------- */
    @Transactional
    public StandardSectionResponseDTO createSection(CreateStandardSectionRequestDTO dto) {
        Standard standard = standardRepository.findById(dto.getStandardId())
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found with ID: " + dto.getStandardId()));

        StandardSection section = new StandardSection();
        section.setNumber(dto.getNumber());
        section.setTitle(dto.getTitle());
        section.setOrderIndex(dto.getOrderIndex());
        section.setStandard(standard);
        section.setCreatedAt(LocalDateTime.now());
        section.setModifiedAt(LocalDateTime.now());

        if (dto.getParentSectionId() != null) {
            StandardSection parent = standardSectionRepository.findById(dto.getParentSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent section not found with ID: " + dto.getParentSectionId()));
            section.setParentSection(parent);
        }

        section = standardSectionRepository.save(section);
        return StandardSectionMapper.toDTO(section);
    }

    @Transactional
    public StandardSectionTreeDTO getStandardSectionTree(Integer standardId) {
        // 1. Fetch the standard
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Standard not found with ID: " + standardId
                ));

        // 2. Fetch its root sections (those without parent)
        List<StandardSection> rootSections = standard.getSections().stream()
                .filter(section -> section.getParentSection() == null)
                .toList();

        // 3. Build the tree recursively
        List<SectionNodeDTO> rootSectionDTOs = rootSections.stream()
                .map(this::mapSectionToTreeNode)
                .toList();

        // 4. Return final tree
        StandardSectionTreeDTO dto = new StandardSectionTreeDTO();
        dto.setId(standard.getStandardId());   // careful: use the correct getter from your entity
        dto.setTitle(standard.getName());
        dto.setVersion(standard.getVersion());
        dto.setSections(rootSectionDTOs);

        return dto;
    }

    /**
     * Recursively maps a StandardSection into  SectionNodeDTO
     */
    private SectionNodeDTO mapSectionToTreeNode(StandardSection section) {
        // Map children recursively
        List<SectionNodeDTO> childNodes = section.getChildSections().stream()
                .map(this::mapSectionToTreeNode)
                .toList();

        // Build and return node
        SectionNodeDTO node = new SectionNodeDTO(
                section.getSectionId(),
                section.getNumber(),
                section.getTitle(),
                section.getOrderIndex(),
                childNodes
        );
        return node;
    }



    private void linkFileToSection(Integer fileId, Integer sectionId) {
        FileStorage file = fileStorageService.getFileById(fileId); // optional: fetch via service
        StandardSection section = standardSectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        StandardTemplate template = new StandardTemplate();
        template.setFile(file);
        template.setSection(section);
        standardTemplateRepository.save(template);
    }






    @Transactional
    public FileStorageResponseDTO uploadFileToSection(MultipartFile file, Integer userId, Integer sectionId) {

        // 1. Fetch section & standard (domain validation)
        StandardSection section = standardSectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with ID: " + sectionId));

        // 2. Delegate the file saving to FileStorageService
        FileStorageResponseDTO fileDTO = fileStorageService.uploadFile(file, userId , null);

        // 3. Link file to section (creates StandardTemplate)
        linkFileToSection(fileDTO.getFileId(), sectionId);

        return fileDTO;
    }




    @Transactional
    public void removeFileFromSection(Integer fileId, Integer sectionId) {

        // 0️⃣ Check if file exists
        fileStorageService.getFileById(fileId); // throws exception if file not found

        // 1️⃣ Remove link in StandardTemplate table
        StandardTemplate template = standardTemplateRepository
                .findByFile_FileIdAndSection_SectionId(fileId, sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("File not linked to section"));

        standardTemplateRepository.delete(template);

        // 2️⃣ Delete file metadata via FileStorageService
        fileStorageService.deleteFile(fileId);
    }




    @Transactional
    public Standard findStandardById(Integer standardId) {
        Standard standard = standardRepository.findById(standardId).orElseThrow(
                () -> new ResourceNotFoundException("Standard not found with ID: " + standardId));
        return standard;
    }



    @Transactional
    public Set<StandardTemplate> getTemplatesByStandardId(Integer standardId) {
        // 1️⃣ Fetch the standard (throws if not found)
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found with ID: " + standardId));

        // 2️⃣ Collect all templates from all sections of this standard
        Set<StandardTemplate> templates = standard.getSections().stream()
                .flatMap(section -> section.getTemplates().stream())
                .collect(Collectors.toSet());

//        My own classical implementation
//        Set<StandardSection> standardSections = standard.getSections();
//        Set<StandardTemplate> standardTemplatesFinal = new HashSet<>();
//        for(StandardSection standardSection : standardSections) {
//            for(StandardTemplate standardTemplate : standardSection.getTemplates()) {
//                standardTemplatesFinal.add(standardTemplate);
//            }
//        }

        return templates;
    }



    @Transactional
    public StandardResponseDTO deleteSectionFromStandard(Integer standardId, Integer sectionId, boolean cascade) {
        // validate standard & section
        Standard standard = standardRepository.findById(standardId)
                .orElseThrow(() -> new IllegalArgumentException("Standard not found with ID " + standardId));

        StandardSection section = standardSectionRepository.findById(sectionId)
                .orElseThrow(() -> new IllegalArgumentException("Section not found with ID " + sectionId));

        if (!section.getStandard().getStandardId().equals(standardId)) {
            throw new IllegalArgumentException("Section does not belong to the specified Standard");
        }

        boolean hasChildren = section.getChildSections() != null && !section.getChildSections().isEmpty();
        if (hasChildren && !cascade) {
            throw new IllegalStateException("Cannot delete this section because it has child sections. Enable cascade delete to proceed or delete children first.");
        }

        // If section has templates and cascade == false, block as well (optional - choose desired behavior)
        boolean hasTemplates = section.getTemplates() != null && !section.getTemplates().isEmpty();
        if (hasTemplates && !cascade) {
            throw new IllegalStateException("Cannot delete this section because it has templates/files. Enable cascade delete to proceed or delete templates first.");
        }

        // Check if the section's standard is used by any project (prevent logical break)
        if (projectRepository.existsByStandard_StandardId(standardId)) {
            throw new IllegalStateException("Cannot delete this section because its parent standard is used by one or more projects.");
        }

        // Perform recursive delete (this will handle templates/files safely)
        deleteSectionRecursively(section);

        // Update standard timestamp
        standard.setModifiedAt(LocalDateTime.now());
        standardRepository.save(standard);

        return StandardMapper.toResponseDTO(standard);
    }

    private void deleteSectionRecursively(StandardSection section) {
        // Defensive copies to avoid ConcurrentModificationException and to detach iteration from changes
        var children = new HashSet<>(section.getChildSections() == null ? Set.of() : section.getChildSections());
        for (StandardSection child : children) {
            deleteSectionRecursively(child);
        }

        // Handle templates -> file deletion
        var templates = new HashSet<>(section.getTemplates() == null ? Set.of() : section.getTemplates());
        for (StandardTemplate template : templates) {
            FileStorage file = template.getFile();

            // Delete the template row first (unlink section -> template)
            standardTemplateRepository.delete(template);

            // Now check if the file is referenced anywhere else (other templates, project docs, revisions)
            if (file != null && file.getFileId() != null) {
                Integer fileId = file.getFileId();
                boolean stillReferenced =
                        standardTemplateRepository.existsByFile_FileId(fileId) ||
                                projectDocumentRepository.existsByFile_FileId(fileId) ||
                                documentRevisionRepository.existsByFile_FileId(fileId);

                if (!stillReferenced) {
                    // call your file deletion service (make sure it deletes physical file AND DB record if desired)
                    fileStorageService.deleteFile(fileId);
                }
            }
        }

        // Finally delete the section itself
        standardSectionRepository.delete(section);
    }



}
