package com.hajji.springbootbasics.dto.file;

import java.time.LocalDateTime;

public class FileStorageResponseDTO {
    private Integer fileId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    private Integer uploadedByUserId;
    private Integer uploadedByCustomerId;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Integer getUploadedByUserId() {
        return uploadedByUserId;
    }

    public void setUploadedByUserId(Integer uploadedByUserId) {
        this.uploadedByUserId = uploadedByUserId;
    }


    public Integer getUploadedByCustomerId() {
        return uploadedByCustomerId;
    }

    public void setUploadedByCustomerId(Integer uploadedByCustomerId) {
        this.uploadedByCustomerId = uploadedByCustomerId;
    }
}
