package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponse;
import com.hajji.springbootbasics.dto.status.*;
import com.hajji.springbootbasics.service.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /* ==================== Standard Status ==================== */

    @PostMapping("/standard/create")
    public ResponseEntity<ApiResponse<StandardStatusResponseDTO>> createStandardStatus(
            @RequestBody CreateStandardStatusRequestDTO dto) {
        StandardStatusResponseDTO responseDTO = statusService.createStandardStatus(dto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Standard status created successfully", responseDTO));
    }

    @GetMapping("/standard/all")
    public ResponseEntity<ApiResponse<List<StandardStatusResponseDTO>>> getAllStandardStatuses() {
        List<StandardStatusResponseDTO> statuses = statusService.getAllStandardStatuses();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Standard statuses fetched successfully", statuses));
    }

    /* ==================== Document Status ==================== */

    @PostMapping("/document/create")
    public ResponseEntity<ApiResponse<DocumentStatusResponseDTO>> createDocumentStatus(
            @RequestBody CreateDocumentStatusRequestDTO dto) {
        DocumentStatusResponseDTO responseDTO = statusService.createDocumentStatus(dto);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Document status created successfully", responseDTO));
    }

    @GetMapping("/document/all")
    public ResponseEntity<ApiResponse<List<DocumentStatusResponseDTO>>> getAllDocumentStatuses() {
        List<DocumentStatusResponseDTO> statuses = statusService.getAllDocumentStatuses();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Document statuses fetched successfully", statuses));
    }
}
