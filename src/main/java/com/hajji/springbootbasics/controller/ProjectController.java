package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.project.AssignUserToProjectRequestDTO;
import com.hajji.springbootbasics.dto.project.CreateProjectRequestDTO;
import com.hajji.springbootbasics.dto.project.ProjectResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/project/")
@Tag(
        name = "Project Controller",
        description = "Handles project management operations such as creating projects, assigning users, deleting projects, and uploading new document versions."
)
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("create")
    @Operation(
            summary = "Create a new project",
            description = "Creates a new project using the provided details such as name, description, and customer information."
    )
    public ResponseEntity<ApiResponseWrapper<ProjectResponseDTO>> createProject(
            @Valid @RequestBody CreateProjectRequestDTO dto) {

        ProjectResponseDTO project = projectService.createProject(dto);

        ApiResponseWrapper<ProjectResponseDTO> response = new ApiResponseWrapper<>(
                200,
                "Project created successfully",
                project
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/assign")
    @Operation(
            summary = "Assign a user to a project with a role",
            description = "Assigns a specific user to a given project and associates them with a defined role (e.g., manager, developer)."
    )
    public ResponseEntity<ApiResponseWrapper<String>> assignUserToProject(
            @Valid @RequestBody AssignUserToProjectRequestDTO request) {

        projectService.assignUserWithRole(
                request.getProjectId(),
                request.getUserId(),
                request.getRoleId()
        );

        ApiResponseWrapper<String> apiResponse = new ApiResponseWrapper<>(200, "User assigned with role successfully");

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("delete/{projectId}")
    @Operation(
            summary = "Delete a project",
            description = "Deletes a project permanently based on the given project ID."
    )
    public ResponseEntity<ApiResponseWrapper<String>> deleteProject(
            @PathVariable Integer projectId) {

        projectService.deleteProject(projectId);

        ApiResponseWrapper<String> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Project deleted successfully"
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/{projectId}/documents/{projectDocumentId}/upload")
    @Operation(
            summary = "Upload a new version of a project document",
            description = "Uploads a new version of an existing project document, optionally including a change note for version tracking."
    )
    public ResponseEntity<ApiResponseWrapper<String>> uploadNewVersion(
            @PathVariable int projectId,
            @PathVariable int projectDocumentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") int customerId,
            @RequestParam(value = "changeNote", required = false) String changeNote) {

        projectService.uploadNewVersion(projectId, projectDocumentId, file, customerId, changeNote);

        return ResponseEntity.ok(
                new ApiResponseWrapper<>(200, "File uploaded and revision recorded successfully.")
        );
    }

}
