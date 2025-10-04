package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.project.ProjectDocumentDTO;
import com.hajji.springbootbasics.model.ProjectDocument;

public class ProjectDocumentMapper {
    public static ProjectDocumentDTO toDTO(ProjectDocument document) {
        if (document == null) return null;

        ProjectDocumentDTO dto = new ProjectDocumentDTO();
        dto.setProjectDocumentId(document.getProjectDocumentId());
        dto.setTemplateId(document.getTemplate().getTemplateId());
        dto.setFileId(document.getFile().getFileId());
        dto.setStatusId(document.getStatus().getStatusId());
        dto.setVersionNumber(document.getVersionNumber());
        dto.setLastModifiedBy(document.getLastModifiedBy() != null ? document.getLastModifiedBy().getUserId() : null);
        dto.setCreatedAt(document.getCreatedAt());
        dto.setModifiedAt(document.getModifiedAt());

        return dto;
    }
}
