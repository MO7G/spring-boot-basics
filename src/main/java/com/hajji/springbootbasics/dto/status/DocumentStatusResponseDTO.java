package com.hajji.springbootbasics.dto.status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class DocumentStatusResponseDTO {
    private Integer statusId;
    private String statusName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
// Getters & Setters
}
