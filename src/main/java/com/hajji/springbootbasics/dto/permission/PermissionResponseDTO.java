package com.hajji.springbootbasics.dto.permission;

import java.time.LocalDateTime;

public class PermissionResponseDTO {
    private Integer permissionId;
    private String permissionName;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Integer getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
