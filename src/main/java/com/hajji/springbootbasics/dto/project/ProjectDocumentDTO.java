package com.hajji.springbootbasics.dto.project;

import java.time.LocalDateTime;

public class ProjectDocumentDTO {
    private Integer projectDocumentId;
    private Integer templateId;
    private Integer fileId;
    private Integer statusId;
    private Integer versionNumber;
    private Integer lastModifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Getters and setters
    public Integer getProjectDocumentId() { return projectDocumentId; }
    public void setProjectDocumentId(Integer projectDocumentId) { this.projectDocumentId = projectDocumentId; }

    public Integer getTemplateId() { return templateId; }
    public void setTemplateId(Integer templateId) { this.templateId = templateId; }

    public Integer getFileId() { return fileId; }
    public void setFileId(Integer fileId) { this.fileId = fileId; }

    public Integer getStatusId() { return statusId; }
    public void setStatusId(Integer statusId) { this.statusId = statusId; }

    public Integer getVersionNumber() { return versionNumber; }
    public void setVersionNumber(Integer versionNumber) { this.versionNumber = versionNumber; }

    public Integer getLastModifiedBy() { return lastModifiedBy; }
    public void setLastModifiedBy(Integer lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }



}
