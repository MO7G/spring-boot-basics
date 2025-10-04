package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.status.*;
import com.hajji.springbootbasics.model.ProjectStatus;
import com.hajji.springbootbasics.model.DocumentStatus;

import java.time.LocalDateTime;

public class StatusMapper {

    /* ----------- StandardStatus ----------- */
    public static ProjectStatus toEntity(CreateStandardStatusRequestDTO dto) {
        ProjectStatus status = new ProjectStatus();
        status.setStatusName(dto.getStatusName());
        status.setDescription(dto.getDescription());
        status.setCreatedAt(LocalDateTime.now());
        status.setModifiedAt(LocalDateTime.now());
        return status;
    }

    public static StandardStatusResponseDTO toDTO(ProjectStatus status) {
        StandardStatusResponseDTO dto = new StandardStatusResponseDTO();
        dto.setStatusId(status.getStatusId());
        dto.setStatusName(status.getStatusName());
        dto.setDescription(status.getDescription());
        dto.setCreatedAt(status.getCreatedAt());
        dto.setModifiedAt(status.getModifiedAt());
        return dto;
    }

    /* ----------- DocumentStatus ----------- */
    public static DocumentStatus toEntity(CreateDocumentStatusRequestDTO dto) {
        DocumentStatus status = new DocumentStatus();
        status.setStatusName(dto.getStatusName());
        status.setDescription(dto.getDescription());
        status.setCreatedAt(LocalDateTime.now());
        status.setModifiedAt(LocalDateTime.now());
        return status;
    }

    public static DocumentStatusResponseDTO toDTO(DocumentStatus status) {
        DocumentStatusResponseDTO dto = new DocumentStatusResponseDTO();
        dto.setStatusId(status.getStatusId());
        dto.setStatusName(status.getStatusName());
        dto.setDescription(status.getDescription());
        dto.setCreatedAt(status.getCreatedAt());
        dto.setModifiedAt(status.getModifiedAt());
        return dto;
    }
}
