package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.role.CreateRoleDTO;
import com.hajji.springbootbasics.dto.role.RoleResponseDTO;
import com.hajji.springbootbasics.model.Role;

public class RoleMapper {
    public static RoleResponseDTO toRoleResponseDTO(Role role){
        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
        roleResponseDTO.setRoleId(role.getRoleId());
        roleResponseDTO.setRoleName(role.getRoleName());
        roleResponseDTO.setDescription(role.getDescription());
        roleResponseDTO.setPermissions(role.getPermissions().stream().map(permission -> permission.getPermissionName()).toList());
        roleResponseDTO.setCreateTime(role.getCreatedAt());
        roleResponseDTO.setUpdateTime(role.getCreatedAt());
        return roleResponseDTO;
    }




    public static Role toRole(CreateRoleDTO createRoleDTO){
        Role role = new Role();
        role.setRoleName(createRoleDTO.getRoleName());
        role.setDescription(createRoleDTO.getDescription());
        return role;
    }

}
