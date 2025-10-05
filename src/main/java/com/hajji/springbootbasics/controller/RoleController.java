package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
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
    public ResponseEntity<ApiResponseWrapper<List<RoleResponseDTO>>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRoles();

        ApiResponseWrapper<List<RoleResponseDTO>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Roles fetched successfully",
                roles
        );

        return ResponseEntity.ok(response);
    }



    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<RoleResponseDTO>> createRole(@Valid @RequestBody CreateRoleDTO createRoleDTO) {
        RoleResponseDTO role = roleService.createRole(createRoleDTO);

        ApiResponseWrapper<RoleResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Created Role successfully",
                role
        );

        return ResponseEntity.ok(response);
    }


    @PostMapping("/addPermission")
    public ResponseEntity<ApiResponseWrapper<RoleResponseDTO>> addPermissionToRole(
            @Valid @RequestBody RolePermissionRequestDTO rolePermissionRequestDTO) {

        RoleResponseDTO updatedRole = roleService.addPermission(rolePermissionRequestDTO);

        ApiResponseWrapper<RoleResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Permission added to role successfully",
                updatedRole
        );

        return ResponseEntity.ok(response);
    }




    @DeleteMapping("/unassignPermission")
    public ResponseEntity<ApiResponseWrapper<RoleResponseDTO>> unassignPermissionFromRole(
            @Valid @RequestBody RolePermissionRequestDTO rolePermissionRequestDTO) {

        RoleResponseDTO updatedRole = roleService.unassignPermission(rolePermissionRequestDTO);

        ApiResponseWrapper<RoleResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Permission removed from role successfully",
                updatedRole
        );

        return ResponseEntity.ok(response);
    }







}
