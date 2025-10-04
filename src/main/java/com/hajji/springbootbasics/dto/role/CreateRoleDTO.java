package com.hajji.springbootbasics.dto.role;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateRoleDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Z_]+$", message = "Role name must be uppercase with underscores")
    private String roleName;

    @Size(max =500 , message = "can't be more than 500 characters")
    private String description;

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public void setDescription(String description) {

    }
}
