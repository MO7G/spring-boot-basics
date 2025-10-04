package com.hajji.springbootbasics.dto.file;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadRequestDTO {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
