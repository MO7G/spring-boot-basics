package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponse;
import com.hajji.springbootbasics.dto.role.CreateRoleDTO;
import com.hajji.springbootbasics.dto.role.RolePermissionRequestDTO;
import com.hajji.springbootbasics.dto.role.RoleResponseDTO;
import com.hajji.springbootbasics.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role/")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // ✅ Get All Roles

    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRoles();

        ApiResponse<List<RoleResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Roles fetched successfully",
                roles
        );

        return ResponseEntity.ok(response);
    }



    @PostMapping("create")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(@Valid @RequestBody CreateRoleDTO createRoleDTO) {
        RoleResponseDTO role = roleService.createRole(createRoleDTO);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Created Role successfully",
                role
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/addPermission")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> addPermissionToRole(
            @Valid @RequestBody RolePermissionRequestDTO rolePermissionRequestDTO) {

        RoleResponseDTO updatedRole = roleService.addPermission(rolePermissionRequestDTO);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Permission added to role successfully",
                updatedRole
        );

        return ResponseEntity.ok(response);
    }




    @DeleteMapping("/unassignPermission")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> unassignPermissionFromRole(
            @Valid @RequestBody RolePermissionRequestDTO rolePermissionRequestDTO) {

        RoleResponseDTO updatedRole = roleService.unassignPermission(rolePermissionRequestDTO);

        ApiResponse<RoleResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Permission removed from role successfully",
                updatedRole
        );

        return ResponseEntity.ok(response);
    }







}
