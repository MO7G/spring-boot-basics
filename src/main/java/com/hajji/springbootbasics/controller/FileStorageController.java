package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.service.FileStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files/")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("templateUpload/upload")
    public ResponseEntity<ApiResponseWrapper<FileStorageResponseDTO>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId) {

        FileStorageResponseDTO response = fileStorageService.uploadFile(file, userId, null);
        ApiResponseWrapper apiResponse = new ApiResponseWrapper(
                HttpStatus.OK.value(),
                "Uploaded File Successfuly",
                response
        );
        return ResponseEntity.ok(apiResponse);
    }
}
