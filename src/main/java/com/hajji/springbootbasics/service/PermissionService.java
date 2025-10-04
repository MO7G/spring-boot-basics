package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.permission.CreatePermissionRequestDTO;
import com.hajji.springbootbasics.dto.permission.PermissionResponseDTO;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.permission.PermissionAlreadyExists;
import com.hajji.springbootbasics.mapper.PermissionMapper;
import com.hajji.springbootbasics.model.Permission;
import com.hajji.springbootbasics.model.Role;
import com.hajji.springbootbasics.repository.PermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PermissionService {
    private PermissionRepository permissionRepository;
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionResponseDTO> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionResponseDTO> permissionResponseDTOS = new ArrayList<>();
        for(Permission permission:permissions){
           permissionResponseDTOS.add(PermissionMapper.toDTO(permission));
        }
        return permissionResponseDTOS;
    }


    public PermissionResponseDTO createPermission(CreatePermissionRequestDTO createPermissionRequestDTO) {
        if(permissionRepository.existsByPermissionName(createPermissionRequestDTO.getPermissionName())){
            throw new PermissionAlreadyExists("This permission Already Exists");
        }
        Permission permission = PermissionMapper.toEntity(createPermissionRequestDTO);
        permissionRepository.save(permission);
        return PermissionMapper.toDTO(permission);
    }


    // must be wrapped with transaction because if something went wrong in the middle of deletion the database will roll back safely
    @Transactional
    public void deletePermission(Integer id, boolean forceDelete) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This Permission is not found"));

        // Simulating force Delete if there is a foreign key constrain or something
        if (forceDelete) {
            Set<Role> roles = permission.getRoles();
            if (!roles.isEmpty()) {
                for (Role role : roles) {
                    // Remove the permission from the role's set
                    role.getPermissions().remove(permission);
                }
            }

            // Here we clear the roles from the set of roles in permission to break the bidirectional link
            permission.getRoles().clear();

            // now i can delete everything becuase there is no any linking
            permissionRepository.delete(permission);

        } else {
            // If permission is still linked to roles, we cannot delete
            if (!permission.getRoles().isEmpty()) {
                throw new IllegalStateException("Cannot delete permission. It is still assigned to roles  " +
                        "Use forceDelete=true to remove it from all roles first.");
            }

            // there is no linked roles then i can delete because there is no foreign key constrain
            permissionRepository.delete(permission);
            System.out.println("Permission deleted successfully.");
        }
    }

    public Permission getPermissionById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with ID: " + id));
    }





}
