package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponse;
import com.hajji.springbootbasics.dto.standard.*;
import com.hajji.springbootbasics.service.StandardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/standard/")
public class StandardController {

    private final StandardService standardService;

    public StandardController(StandardService standardService) {
        this.standardService = standardService;
    }

    /* ---------------- STANDARD CRUD ---------------- */

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<StandardResponseDTO>>> getAllStandards() {
        List<StandardResponseDTO> standards = standardService.getAllStandards();

        ApiResponse<List<StandardResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Standards fetched successfully",
                standards
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<StandardResponseDTO>> createStandard(
            @Valid @RequestBody CreateStandardRequestDTO requestDTO) {

        StandardResponseDTO standard = standardService.createStandard(requestDTO);

        ApiResponse<StandardResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Standard created successfully",
                standard
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("delete/{standardId}")
    public ResponseEntity<ApiResponse<String>> deleteStandard(@PathVariable Integer standardId) {
        standardService.deleteStandard(standardId);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Standard deleted successfully",
                "Standard ID: " + standardId
        );

        return ResponseEntity.ok(response);
    }

    /* ---------------- SECTION CREATE ---------------- */

    @PostMapping("{standardId}/sections/create")
    public ResponseEntity<ApiResponse<StandardSectionResponseDTO>> createSection(
            @PathVariable Integer standardId,
            @Valid @RequestBody CreateStandardSectionRequestDTO dto) {

        dto.setStandardId(standardId); // enforce standard from URL
        StandardSectionResponseDTO section = standardService.createSection(dto);

        ApiResponse<StandardSectionResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Standard section created successfully",
                section
        );

        return ResponseEntity.ok(response);
    }



    @GetMapping("{standardId}/treeView")
    public ResponseEntity<ApiResponse<StandardSectionTreeDTO>> getStandardSectionTree(
            @PathVariable Integer standardId) {

        StandardSectionTreeDTO tree = standardService.getStandardSectionTree(standardId);

        ApiResponse<StandardSectionTreeDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Standard Tree Fetched successfully",
                tree
        );

        return ResponseEntity.ok(response);
    }



    /*--------------File upload here --------------------*/
    @PostMapping("{standardId}/sections/{sectionId}/upload")
    public ResponseEntity<ApiResponse<FileStorageResponseDTO>> uploadFileToSection(
            @PathVariable Integer standardId,
            @PathVariable Integer sectionId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId) {

        // Delegate to service
        FileStorageResponseDTO uploaded = standardService.uploadFileToSection(file , userId, sectionId);

        ApiResponse<FileStorageResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "File uploaded successfully",
                uploaded
        );

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("{standardId}/sections/{sectionId}/files/{fileId}")
    public ResponseEntity<ApiResponse<String>> deleteFileFromSection(
            @PathVariable Integer standardId,
            @PathVariable Integer sectionId,
            @PathVariable Integer fileId
    ) {
        standardService.removeFileFromSection(fileId, sectionId);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "File removed successfully",
                "File ID: " + fileId + " from Section ID: " + sectionId
        );

        return ResponseEntity.ok(response);
    }



}
