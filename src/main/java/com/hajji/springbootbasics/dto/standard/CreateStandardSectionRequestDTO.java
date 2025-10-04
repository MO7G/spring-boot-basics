package com.hajji.springbootbasics.dto.standard;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateStandardSectionRequestDTO {

    private Integer standardId;

    @NotBlank(message = "Section number is required")
    @Size(max = 50)
    private String number;

    @NotBlank(message = "Section title is required")
    @Size(max = 200)
    private String title;

    private Integer orderIndex;


    private Integer parentSectionId; // optional

    // Getters & Setters
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }

    public Integer getStandardId() { return standardId; }
    public void setStandardId(Integer standardId) { this.standardId = standardId; }

    public Integer getParentSectionId() { return parentSectionId; }
    public void setParentSectionId(Integer parentSectionId) { this.parentSectionId = parentSectionId; }
}
