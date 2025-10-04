package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.project.ProjectResponseDTO;
import com.hajji.springbootbasics.dto.project.ProjectDocumentDTO;
import com.hajji.springbootbasics.model.Project;
import com.hajji.springbootbasics.model.ProjectDocument;
import org.hibernate.engine.spi.ComparableExecutable;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectResponseDTO toDTO(Project project) {
        if (project == null) return null;



        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setProjectId(project.getProjectId());
        dto.setCustomerId(project.getCustomer().getCustomerId());
        dto.setStandardId(project.getStandard().getStandardId());
        dto.setStatusId(project.getStatus().getStatusId());
        dto.setStartDate(project.getStartDate());
        dto.setCompletionDate(project.getCompletionDate());

        // Map project documents
        List<ProjectDocumentDTO> documentDTOs = project.getProjectDocuments().stream()
                .map(ProjectDocumentMapper::toDTO)
                .sorted(Comparator.comparing(ProjectDocumentDTO::getCreatedAt))
                        .toList();

        dto.setProjectDocuments(documentDTOs.stream().toList());

        return dto;
    }
}
