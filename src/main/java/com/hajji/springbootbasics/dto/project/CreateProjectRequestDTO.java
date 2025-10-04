package com.hajji.springbootbasics.dto.project;

import jakarta.validation.constraints.NotNull;

public class CreateProjectRequestDTO {

    @NotNull
    private Integer customerId;

    @NotNull
    private Integer standardId;

    // Getters & Setters
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
}
