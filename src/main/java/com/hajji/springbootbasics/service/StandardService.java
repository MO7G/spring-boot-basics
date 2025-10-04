package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.standard.*;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.fileStorage.InvalidFileTypeException;
import com.hajji.springbootbasics.mapper.StandardMapper;
import com.hajji.springbootbasics.mapper.StandardSectionMapper;
import com.hajji.springbootbasics.model.*;
import com.hajji.springbootbasics.repository.StandardRepository;
import com.hajji.springbootbasics.repository.StandardSectionRepository;
import com.hajji.springbootbasics.repository.StandardTemplateRepository;
import jakarta.transaction.Transactional;
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
    private final StandardSectionRepository sectionRepository;
    private UserService userService;
    private FileStorageService fileStorageService;
    private StandardTemplateRepository standardTemplateRepository;
    public StandardService(StandardRepository standardRepository,
                           StandardSectionRepository sectionRepository,
                           UserService userService,
                           FileStorageService fileStorageService,
                           StandardTemplateRepository standardTemplateRepository) {
        this.standardRepository = standardRepository;
        this.sectionRepository = sectionRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.standardTemplateRepository = standardTemplateRepository;
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

        return StandardMapper.toDTO(standard);
    }

    @Transactional
    public List<StandardResponseDTO> getAllStandards() {
        return standardRepository.findAll()
                .stream()
                .map(StandardMapper::toDTO)
                .toList();
    }

    @Transactional
    public StandardResponseDTO getStandardById(Integer id) {
        Standard standard = standardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standard not found with ID: " + id));

        return StandardMapper.toDTO(standard);
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
            StandardSection parent = sectionRepository.findById(dto.getParentSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent section not found with ID: " + dto.getParentSectionId()));
            section.setParentSection(parent);
        }

        section = sectionRepository.save(section);
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
        StandardSection section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        StandardTemplate template = new StandardTemplate();
        template.setFile(file);
        template.setSection(section);
        standardTemplateRepository.save(template);
    }






    @Transactional
    public FileStorageResponseDTO uploadFileToSection(MultipartFile file, Integer userId, Integer sectionId) {

        // 1. Fetch section & standard (domain validation)
        StandardSection section = sectionRepository.findById(sectionId)
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



}
