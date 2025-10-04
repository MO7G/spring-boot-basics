package com.hajji.springbootbasics.dto.status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/* ----------- StandardStatus DTOs ----------- */
public class CreateStandardStatusRequestDTO {
    @NotBlank
    @Size(max = 50)
    private String statusName;

    @Size(max = 200)
    private String description;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
// Getters & Setters
}
