package com.hajji.springbootbasics.dto.role;

import com.hajji.springbootbasics.dto.permission.PermissionResponseDTO;

import java.time.LocalDateTime;
import java.util.Set;

public class RolePermissionDTO {
    private Integer roleId;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Set<PermissionResponseDTO> permissions;
}
