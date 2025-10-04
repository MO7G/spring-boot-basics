package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.permission.CreatePermissionRequestDTO;
import com.hajji.springbootbasics.dto.permission.PermissionResponseDTO;
import com.hajji.springbootbasics.model.Permission;

public class PermissionMapper {

    public static Permission toEntity(CreatePermissionRequestDTO createPermissionRequestDTO){
        Permission permission = new Permission();
        permission.setPermissionName(createPermissionRequestDTO.getPermissionName());
        permission.setDescription(createPermissionRequestDTO.getDescription());
        return permission;
    }


    public static PermissionResponseDTO toDTO(Permission permission){
        PermissionResponseDTO permissionResponseDTO = new PermissionResponseDTO();
        permissionResponseDTO.setPermissionId(permission.getPermissionId());
        permissionResponseDTO.setPermissionName(permission.getPermissionName());
        permissionResponseDTO.setDescription(permission.getDescription());
        permissionResponseDTO.setCreateTime(permission.getCreatedAt());
        permissionResponseDTO.setUpdateTime(permission.getCreatedAt());
        return permissionResponseDTO;
    }
}
