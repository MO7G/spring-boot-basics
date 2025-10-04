package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.project.AssignUserToProjectRequestDTO;
import com.hajji.springbootbasics.dto.project.CreateProjectRequestDTO;
import com.hajji.springbootbasics.dto.project.ProjectResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponse;
import com.hajji.springbootbasics.model.ProjectDocument;
import com.hajji.springbootbasics.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/project/")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> createProject(
            @Valid @RequestBody CreateProjectRequestDTO dto) {

        ProjectResponseDTO project = projectService.createProject(dto);

        ApiResponse<ProjectResponseDTO> response = new ApiResponse<>(
                200,
                "Project created successfully",
                project
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<String>> assignUserToProject(
            @Valid @RequestBody AssignUserToProjectRequestDTO request) {

        projectService.assignUserWithRole(
                request.getProjectId(),
                request.getUserId(),
                request.getRoleId()
        );

        ApiResponse<String> apiResponse = new ApiResponse<>(200, "User assigned with role successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("delete/{projectId}")
    public ResponseEntity<ApiResponse<String>> deleteProject(
            @PathVariable Integer projectId) {

        projectService.deleteProject(projectId);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Project deleted successfully"
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/{projectId}/documents/{projectDocumentId}/upload")
    public ResponseEntity<ApiResponse<String>> uploadNewVersion(
            @PathVariable int projectId,
            @PathVariable int projectDocumentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") int customerId,
            @RequestParam(value = "changeNote", required = false) String changeNote) {

        projectService.uploadNewVersion(projectId, projectDocumentId, file, customerId, changeNote);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "File uploaded and revision recorded successfully.")
        );
    }

}
