package com.hajji.springbootbasics.dto.project;

import jakarta.validation.constraints.NotNull;

public class AssignUserToProjectRequestDTO {

    @NotNull(message = "Project ID is required")
    private Integer projectId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    // Role is optional - in case you want to assign role immediately
    private Integer roleId;

    // --- Getters & Setters ---
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
