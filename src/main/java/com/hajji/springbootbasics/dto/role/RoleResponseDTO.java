package com.hajji.springbootbasics.dto.role;

import java.time.LocalDateTime;
import java.util.List;

public class RoleResponseDTO {
    private Integer roleId;
    private String roleName;
    private String description;
    private List<String> permissions;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<String> getPermissions() { return permissions; }


    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;

    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}

