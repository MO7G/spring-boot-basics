package com.hajji.springbootbasics.dto.permission;

import jakarta.validation.constraints.*;

public class CreatePermissionRequestDTO {

    @NotBlank
    @Size(max= 50 , message = "Can't be greate than 50")
    @Pattern(regexp = "^[A-Z_]+$" , message = "Permission must uppercase with underscore only !!")
    private String permissionName;

    @Size(max=500 , message = "Can't be more than 500 characters")
    private String description;

    public String getPermissionName() {
        return permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
