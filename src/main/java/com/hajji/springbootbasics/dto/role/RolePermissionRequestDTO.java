package com.hajji.springbootbasics.dto.role;

import jakarta.validation.constraints.NotNull;

public class RolePermissionRequestDTO {

    @NotNull(message = "Role ID is required")
    private Integer roleId;

    @NotNull(message = "Permission ID is required")
    private Integer permissionId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
