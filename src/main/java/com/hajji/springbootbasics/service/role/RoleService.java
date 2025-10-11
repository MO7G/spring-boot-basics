package com.hajji.springbootbasics.service.role;

import com.hajji.springbootbasics.dto.role.CreateRoleDTO;
import com.hajji.springbootbasics.dto.role.RolePermissionRequestDTO;
import com.hajji.springbootbasics.dto.role.RoleResponseDTO;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.role.RoleAlreadyExistsException;
import com.hajji.springbootbasics.mapper.RoleMapper;
import com.hajji.springbootbasics.model.Permission;
import com.hajji.springbootbasics.model.Role;
import com.hajji.springbootbasics.repository.ProjectAssignmentRepository;
import com.hajji.springbootbasics.repository.RoleRepository;
import com.hajji.springbootbasics.service.PermissionService;
import com.hajji.springbootbasics.service.role.contracts.RoleProjectChecker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionService permissionService; // inject the service
    private final ProjectAssignmentRepository projectAssignmentRepository;
    public RoleService(RoleRepository roleRepository, PermissionService permissionService , ProjectAssignmentRepository projectAssignmentRepository) {
        this.roleRepository = roleRepository;
        this.permissionService = permissionService;
        this.projectAssignmentRepository = projectAssignmentRepository;
    }


    @Transactional
    public List<RoleResponseDTO> getAllRoles(){
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDTO> roleResponseDTOS = new ArrayList<>();
        for (Role role : roles){
            RoleResponseDTO roleResponseDTO = new RoleResponseDTO();
            roleResponseDTO = RoleMapper.toRoleResponseDTO(role);
            roleResponseDTOS.add(roleResponseDTO);
        }
        return roleResponseDTOS;


// using streams from more professinal from chatgpt
//        return roleRepository.findAll()
//                .stream()
//                .map(RoleMapper::toRoleResponseDTO)
//                .toList(); // Java 16+ (or use Collectors.toList() in Java 8–15)
    }


    public RoleResponseDTO createRole(CreateRoleDTO createRoleDTO){
        if(roleRepository.existsByRoleName(createRoleDTO.getRoleName())){
            throw new RoleAlreadyExistsException("Role already exists");
        }
        Role role = RoleMapper.toRole(createRoleDTO);
        role = roleRepository.save(role);
        RoleResponseDTO roleResponseDTO = RoleMapper.toRoleResponseDTO(role);
        return roleResponseDTO;
    }


    @Transactional
    public RoleResponseDTO addPermission(RolePermissionRequestDTO requestDto) {
        // Fetch role
        Role role = roleRepository.findById(requestDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + requestDto.getRoleId()));

        // Fetch permission using the service
        Permission permission = permissionService.getPermissionById(requestDto.getPermissionId());

        // Check if permission already exists
        if (role.getPermissions().contains(permission)) {
            throw new IllegalStateException("Role already has this permission");
        }

        // Add permission (owning side)
        role.getPermissions().add(permission);

        // Optional: update inverse side for in-memory consistency
        permission.getRoles().add(role);

        // Save role
        role = roleRepository.save(role);

        // Map to DTO
        return RoleMapper.toRoleResponseDTO(role);
    }



    @Transactional
    public RoleResponseDTO unassignPermission(RolePermissionRequestDTO requestDto) {

        Role role = roleRepository.findById(requestDto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + requestDto.getRoleId()));


        Permission permission = permissionService.getPermissionById(requestDto.getPermissionId());


        if (!role.getPermissions().contains(permission)) {
            throw new IllegalStateException("Role does not have this permission assigned");
        }


        role.getPermissions().remove(permission);

        permission.getRoles().remove(role);

        role = roleRepository.save(role);

        return RoleMapper.toRoleResponseDTO(role);
    }



    @Transactional
    public void deleteRole(Integer roleId) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));

        if (!role.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete role: it is assigned to users.");
        }

        if (!role.getPermissions().isEmpty()) {
            throw new IllegalStateException("Cannot delete role: it has permissions assigned.");
        }

        if (projectAssignmentRepository.existsByRoles_RoleId(roleId)) {
            throw new IllegalStateException("Cannot delete role: it is assigned to projects.");
        }

        // ✅ Safe to delete
        roleRepository.delete(role);
    }



    @Transactional
    public Role getRoleById(Integer roleId){
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
        return role;
    }


}
