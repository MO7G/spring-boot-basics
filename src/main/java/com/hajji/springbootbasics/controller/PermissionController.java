package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.permission.CreatePermissionRequestDTO;
import com.hajji.springbootbasics.dto.permission.PermissionResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.model.Permission;
import com.hajji.springbootbasics.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission/")
@Tag(name = "Permissions", description = "Endpoints for managing user permissions")
public class PermissionController {
    private PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Operation(
            summary = "Get all permissions",
            description = "Fetches a list of all available permissions in the system."
    )
    @GetMapping("all")
    public ResponseEntity<ApiResponseWrapper<List<Permission>>> getAllPermission(){
        List<PermissionResponseDTO> permissions = permissionService.getAllPermission();
        ApiResponseWrapper<List<Permission>> apiResponse = new  ApiResponseWrapper(
                HttpStatus.OK.value(),
                "Fetched All Permission Successfully",
                permissions
        );

        return ResponseEntity.ok(apiResponse);
    }



    @Operation(
            summary = "Create a new permission",
            description = "Creates a new permission entry using the provided request data."
    )
    @PostMapping("create")
    public ResponseEntity<ApiResponseWrapper<PermissionResponseDTO>> createPermission(@Valid @RequestBody CreatePermissionRequestDTO createPermissionRequestDTO){
        PermissionResponseDTO permissionResponseDTO  = permissionService.createPermission(createPermissionRequestDTO);
        ApiResponseWrapper<PermissionResponseDTO> apiResponse = new  ApiResponseWrapper(
                HttpStatus.OK.value(),
                "Created Permission Successfully",
                permissionResponseDTO
        );

        return ResponseEntity.ok(apiResponse);
    }


    @Operation(
            summary = "Delete a permission",
            description = "Deletes a specific permission identified by its ID. Set forceDelete=true if needed."
    )


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponseWrapper<PermissionResponseDTO>> deletePermission(@PathVariable("id") Integer id){
        permissionService.deletePermission(id,false);
        ApiResponseWrapper<PermissionResponseDTO> apiResponse = new  ApiResponseWrapper(
                HttpStatus.OK.value(),
                "Deleted Permission Successfully",
                null
        );
        return ResponseEntity.ok(apiResponse);
    }

}
