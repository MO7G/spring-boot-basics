package com.hajji.springbootbasics.dto.standard;

import java.time.LocalDateTime;

public class StandardSectionResponseDTO {
    private Integer sectionId;
    private String number;
    private String title;
    private Integer orderIndex;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer standardId;
    private Integer parentSectionId;

    // Getters & Setters
    public Integer getSectionId() { return sectionId; }
    public void setSectionId(Integer sectionId) { this.sectionId = sectionId; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
    public void setModifiedAt(LocalDateTime modifiedAt) { this.modifiedAt = modifiedAt; }

    public Integer getStandardId() { return standardId; }
    public void setStandardId(Integer standardId) { this.standardId = standardId; }

    public Integer getParentSectionId() { return parentSectionId; }
    public void setParentSectionId(Integer parentSectionId) { this.parentSectionId = parentSectionId; }
}
