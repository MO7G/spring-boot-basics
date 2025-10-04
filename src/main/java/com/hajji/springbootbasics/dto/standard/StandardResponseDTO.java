package com.hajji.springbootbasics.dto.standard;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StandardResponseDTO {

    private Integer standardId;
    private String name;
    private String version;
    private LocalDate publishedDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    // Getters & Setters
    public Integer getStandardId() { return standardId; }
    public void setStandardId(Integer standardId) { this.standardId = standardId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }
}
