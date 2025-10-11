package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.dto.status.*;
import com.hajji.springbootbasics.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Status Controller",
        description = "Manages system statuses including Standard Status and Document Status creation and retrieval."
)
@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /* ==================== Standard Status ==================== */
    @Operation(
            summary = "Create a standard status",
            description = "Creates a new standard status record in the system with the provided details."
    )
    @PostMapping("/standard/create")
    public ResponseEntity<ApiResponseWrapper<StandardStatusResponseDTO>> createStandardStatus(
            @RequestBody CreateStandardStatusRequestDTO dto) {
        StandardStatusResponseDTO responseDTO = statusService.createStandardStatus(dto);
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Standard status created successfully", responseDTO));
    }

    @Operation(
            summary = "Get all standard statuses",
            description = "Retrieves a list of all standard statuses available in the system."
    )
    @GetMapping("/standard/all")
    public ResponseEntity<ApiResponseWrapper<List<StandardStatusResponseDTO>>> getAllStandardStatuses() {
        List<StandardStatusResponseDTO> statuses = statusService.getAllStandardStatuses();
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Standard statuses fetched successfully", statuses));
    }

    /* ==================== Document Status ==================== */

    @Operation(
            summary = "Create a document status",
            description = "Creates a new document status entry for tracking document workflow states."
    )
    @PostMapping("/document/create")
    public ResponseEntity<ApiResponseWrapper<DocumentStatusResponseDTO>> createDocumentStatus(
            @RequestBody CreateDocumentStatusRequestDTO dto) {
        DocumentStatusResponseDTO responseDTO = statusService.createDocumentStatus(dto);
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Document status created successfully", responseDTO));
    }

    @Operation(
            summary = "Get all document statuses",
            description = "Fetches all document statuses that define document lifecycle states."
    )
    @GetMapping("/document/all")
    public ResponseEntity<ApiResponseWrapper<List<DocumentStatusResponseDTO>>> getAllDocumentStatuses() {
        List<DocumentStatusResponseDTO> statuses = statusService.getAllDocumentStatuses();
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Document statuses fetched successfully", statuses));
    }



    /* ==================== Delete Standard Status ==================== */
    @Operation(
            summary = "Delete a standard status",
            description = "Deletes a standard status by its ID. Will fail if it is associated with any projects."
    )
    @DeleteMapping("/project/{statusId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteStandardStatus(
            @PathVariable Integer statusId) {
        statusService.deleteStandardStatus(statusId);
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Project status deleted successfully", null));
    }

    /* ==================== Delete Document Status ==================== */
    @Operation(
            summary = "Delete a document status",
            description = "Deletes a document status by its ID. Will fail if it is associated with any project documents."
    )
    @DeleteMapping("/document/{statusId}")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteDocumentStatus(
            @PathVariable Integer statusId) {
        statusService.deleteDocumentStatus(statusId);
        return ResponseEntity.ok(new ApiResponseWrapper<>(HttpStatus.OK.value(),
                "Document status deleted successfully", null));
    }



}
