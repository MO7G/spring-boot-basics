package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.dto.standard.*;
import com.hajji.springbootbasics.service.StandardService;
import com.hajji.springbootbasics.utility.PaginationLogger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/standard")
@Tag(name = "Standards", description = "Endpoints for managing ISO standards, sections, and file uploads")
public class StandardController {

    private final StandardService standardService;

    public StandardController(StandardService standardService) {
        this.standardService = standardService;
    }



    /* ---------------- STANDARD CRUD ---------------- */
    @GetMapping("/all")
    @Operation(
            summary = "Get all standards with pagination",
            description = "Retrieves a paginated list of all ISO standards. Default page=0, size=20."
    )
    public ResponseEntity<ApiResponseWrapper<List<StandardResponseDTO>>> getAllStandards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PaginationLogger.logPageFetch("Standards", pageable);

        List<StandardResponseDTO> standards = standardService.getAllStandards(pageable);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standards fetched successfully",
                standards
        ));
    }




    @PostMapping("/create")
    @Operation(
            summary = "Create a new standard",
            description = "Creates a new ISO standard using the provided details."
    )
    public ResponseEntity<ApiResponseWrapper<StandardResponseDTO>> createStandard(
            @Valid @RequestBody CreateStandardRequestDTO requestDTO) {

        StandardResponseDTO standard = standardService.createStandard(requestDTO);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standard created successfully",
                standard
        ));
    }



    @PatchMapping("/update")
    @Operation(
            summary = "Update a standard",
            description = "Update an ISO standard using the provided details."
    )
    public ResponseEntity<ApiResponseWrapper<StandardResponseDTO>> updateStandard(
            @Valid @RequestBody UpdateStandardRequestDTO requestDTO) {

        StandardResponseDTO standard = standardService.updateStandard(requestDTO);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standard updated successfully",
                standard
        ));
    }



    @DeleteMapping("/delete/{standardId}")
    @Operation(
            summary = "Delete a standard",
            description = "Deletes an existing ISO standard by its ID."
    )
    public ResponseEntity<ApiResponseWrapper<String>> deleteStandard(
            @Parameter(description = "ID of the standard to delete") @PathVariable Integer standardId) {

        standardService.deleteStandard(standardId);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standard deleted successfully",
                "Standard ID: " + standardId
        ));
    }




    /* ---------------- SECTION CRUD ---------------- */

    @PostMapping("/{standardId}/sections/create")
    @Operation(
            summary = "Create a section under a standard",
            description = "Adds a new section to a specific ISO standard identified by its ID."
    )
    public ResponseEntity<ApiResponseWrapper<StandardSectionResponseDTO>> createSection(
            @Parameter(description = "ID of the parent standard") @PathVariable Integer standardId,
            @Valid @RequestBody CreateStandardSectionRequestDTO dto) {

        dto.setStandardId(standardId);
        StandardSectionResponseDTO section = standardService.createSection(dto);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standard section created successfully",
                section
        ));
    }



    @GetMapping("/{standardId}/treeView")
    @Operation(
            summary = "Get standard section tree",
            description = "Fetches the hierarchical tree of all sections for a given standard."
    )
    public ResponseEntity<ApiResponseWrapper<StandardSectionTreeDTO>> getStandardSectionTree(
            @Parameter(description = "ID of the standard") @PathVariable Integer standardId) {

        StandardSectionTreeDTO tree = standardService.getStandardSectionTree(standardId);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Standard tree fetched successfully",
                tree
        ));
    }


    @DeleteMapping("/{standardId}/sections/delete/{sectionId}")
    @Operation(
            summary = "Delete a section from a standard",
            description = "Deletes a section. If cascade=true, deletes all child sections too."
    )
    public ResponseEntity<ApiResponseWrapper<String>> deleteSection(
            @PathVariable Integer standardId,
            @PathVariable Integer sectionId,
            @RequestParam(defaultValue = "true") boolean cascade) {

        standardService.deleteSectionFromStandard(standardId, sectionId, cascade);

        ApiResponseWrapper<String> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                cascade
                        ? "Section and its children deleted successfully"
                        : "Section deleted successfully",
                "Section ID: " + sectionId + " from Standard ID: " + standardId
        );

        return ResponseEntity.ok(response);
    }

















    /* ----------------Section FILE UPLOAD ---------------- */
    @PostMapping("/{standardId}/sections/{sectionId}/upload")
    @Operation(
            summary = "Upload a file to a section",
            description = "Uploads a single PDF file to the specified section. Multiple files per request are not allowed"
    )
    public ResponseEntity<ApiResponseWrapper<FileStorageResponseDTO>> uploadFileToSection(
            @Parameter(description = "ID of the standard") @PathVariable Integer standardId,
            @Parameter(description = "ID of the section") @PathVariable Integer sectionId,
            @Parameter(description = "File to upload (PDF only)") @RequestParam("file") MultipartFile file,
            @Parameter(description = "User ID of the uploader") @RequestParam("userId") Integer userId,
            HttpServletRequest request) {

        if (request instanceof MultipartHttpServletRequest multipartRequest &&
                multipartRequest.getMultiFileMap().get("file").size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Multiple files are not allowed. Upload only one file at a time.");
        }

        FileStorageResponseDTO uploaded = standardService.uploadFileToSection(file, userId, sectionId);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "File uploaded successfully",
                uploaded
        ));
    }


    @DeleteMapping("/{standardId}/sections/{sectionId}/files/{fileId}")
    @Operation(
            summary = "Delete a file from a section",
            description = "Removes a specific file by ID from the given section."
    )
    public ResponseEntity<ApiResponseWrapper<String>> deleteFileFromSection(
            @Parameter(description = "Standard ID") @PathVariable Integer standardId,
            @Parameter(description = "Section ID") @PathVariable Integer sectionId,
            @Parameter(description = "File ID to delete") @PathVariable Integer fileId) {

        standardService.removeFileFromSection(fileId, sectionId);

        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "File removed successfully",
                "File ID: " + fileId + " from Section ID: " + sectionId
        ));
    }
}
