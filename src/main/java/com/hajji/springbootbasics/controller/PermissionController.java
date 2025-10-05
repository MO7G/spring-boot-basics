package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.permission.CreatePermissionRequestDTO;
import com.hajji.springbootbasics.dto.permission.PermissionResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.model.Permission;
import com.hajji.springbootbasics.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission/")
public class PermissionController {
    private PermissionService permissionService;
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

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


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponseWrapper<PermissionResponseDTO>> deletePermission(@PathVariable("id") Integer id){
        permissionService.deletePermission(id,true);
        ApiResponseWrapper<PermissionResponseDTO> apiResponse = new  ApiResponseWrapper(
                HttpStatus.OK.value(),
                "Deleted Permission Successfully",
                null
        );
        return ResponseEntity.ok(apiResponse);
    }

}
