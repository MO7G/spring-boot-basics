package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.dto.role.CreateRoleDTO;
import com.hajji.springbootbasics.dto.role.RolePermissionRequestDTO;
import com.hajji.springbootbasics.dto.role.RoleResponseDTO;
import com.hajji.springbootbasics.service.role.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role/")
@Tag(
        name = "Role Controller",
        description = "Manages user roles and their associated permissions, including creating roles, listing roles, and assigning/unassigning permissions."
)
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }



    @GetMapping("all")
    @Operation(
            summary = "Get all roles",
            description = "Fetches all available roles in the system."
    )
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
    @Operation(
            summary = "Create a new role",
            description = "Creates a new role with the specified name and description."
    )
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
    @Operation(
            summary = "Assign permission to a role",
            description = "Adds a specific permission to the given role based on role and permission IDs."
    )
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




    @PostMapping("/unassignPermission")
    @Operation(
            summary = "Unassign permission from a role",
            description = "Removes an existing permission from a role using the provided role and permission IDs."
    )
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



    @DeleteMapping("/delete/{roleId}")
    @Operation(
            summary = "delete a Role ",
            description = "Deletes an existing role using the role id !!"
    )
    public ResponseEntity<ApiResponseWrapper<String>> deleteRole(@PathVariable Integer roleId) {

         roleService.deleteRole(roleId);

        ApiResponseWrapper<String> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Role removed  successfully",
                null
        );

        return ResponseEntity.ok(response);
    }




}
