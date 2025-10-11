package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.status.*;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.mapper.StatusMapper;
import com.hajji.springbootbasics.model.ProjectStatus;
import com.hajji.springbootbasics.model.DocumentStatus;
import com.hajji.springbootbasics.repository.ProjectStatusRepository;
import com.hajji.springbootbasics.repository.DocumentStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final ProjectStatusRepository projectStatusRepository;
    private final DocumentStatusRepository documentStatusRepository;

    public StatusService(ProjectStatusRepository projectStatusRepository, DocumentStatusRepository documentStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
        this.documentStatusRepository = documentStatusRepository;
    }

    /* ==================== Standard Status ==================== */

    @Transactional
    public StandardStatusResponseDTO createStandardStatus(CreateStandardStatusRequestDTO dto) {
        ProjectStatus status = StatusMapper.toEntity(dto);
        status = projectStatusRepository.save(status);
        return StatusMapper.toDTO(status);
    }

    @Transactional
    public List<StandardStatusResponseDTO> getAllStandardStatuses() {
        return projectStatusRepository.findAll().stream()
                .map(StatusMapper::toDTO)
                .toList();
    }

    /* ==================== Document Status ==================== */

    @Transactional
    public DocumentStatusResponseDTO createDocumentStatus(CreateDocumentStatusRequestDTO dto) {
        DocumentStatus status = StatusMapper.toEntity(dto);
        status = documentStatusRepository.save(status);
        return StatusMapper.toDTO(status);
    }

    @Transactional
    public List<DocumentStatusResponseDTO> getAllDocumentStatuses() {
        return documentStatusRepository.findAll().stream()
                .map(StatusMapper::toDTO)
                .toList();
    }

    @Transactional
    public ProjectStatus findProjectStatusByName(String statusName) {
        ProjectStatus standardStatus = projectStatusRepository.findByStatusNameIgnoreCase(statusName).orElseThrow(
               ()->  new ResourceNotFoundException("Project status not found with name " + statusName)
       );
       return standardStatus;
    }


    @Transactional
    public DocumentStatus findDocumentStatusByName(String statusName) {
        DocumentStatus documentStatus = documentStatusRepository.findByStatusNameIgnoreCase(statusName).orElseThrow(
                ()->  new ResourceNotFoundException("Project status not found with name " + statusName)
        );
        return documentStatus;
    }



    @Transactional
    public void deleteStandardStatus(Integer statusId) {
        ProjectStatus status = projectStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Standard status not found with ID " + statusId));

        // Check if status is associated with any projects
        if (status.getProjects() != null && !status.getProjects().isEmpty()) {
            throw new IllegalStateException("Cannot delete this standard status because it is assigned to one or more projects.");
        }

        projectStatusRepository.delete(status);
    }


    @Transactional
    public void deleteDocumentStatus(Integer statusId) {
        DocumentStatus status = documentStatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Document status not found with ID " + statusId));

        // Check if status is associated with any project documents
        if (status.getProjectDocuments() != null && !status.getProjectDocuments().isEmpty()) {
            throw new IllegalStateException("Cannot delete this document status because it is assigned to one or more project documents.");
        }

        documentStatusRepository.delete(status);
    }


}
