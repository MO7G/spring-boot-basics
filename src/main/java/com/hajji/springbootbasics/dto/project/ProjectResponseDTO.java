package com.hajji.springbootbasics.dto.project;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectResponseDTO {

    private Integer projectId;
    private Integer customerId;
    private Integer standardId;
    private Integer statusId;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    // ✅ Add project documents
    private List<ProjectDocumentDTO> projectDocuments;

    // Getters & Setters
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public List<ProjectDocumentDTO> getProjectDocuments() {
        return projectDocuments;
    }

    public void setProjectDocuments(List<ProjectDocumentDTO> projectDocuments) {
        this.projectDocuments = projectDocuments;
    }

}
