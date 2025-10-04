package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.model.Customer;
import com.hajji.springbootbasics.model.FileStorage;
import com.hajji.springbootbasics.model.User;

public class FileStorageMapper {

    public static FileStorageResponseDTO toResponseDTO(FileStorage file) {
        FileStorageResponseDTO dto = new FileStorageResponseDTO();  dto.setFileId(file.getFileId());
        dto.setFileName(file.getFileName());
        dto.setFilePath(file.getFilePath());
        dto.setFileSize(file.getFileSize());
        dto.setUploadedAt(file.getUploadedAt());

        // Safely handle uploader (user)
        if (file.getUploadedByUser() != null) {
            dto.setUploadedByUserId(file.getUploadedByUser().getUserId());
        }

        // Safely handle uploader (customer)
        if (file.getUploadedByCustomer() != null) {
            dto.setUploadedByCustomerId(file.getUploadedByCustomer().getCustomerId());
        }

        return dto;
    }

    public static FileStorage toEntity(FileStorageResponseDTO dto) {
        FileStorage entity = new FileStorage();

        entity.setFileId(dto.getFileId());
        entity.setFileName(dto.getFileName());
        entity.setFilePath(dto.getFilePath());
        entity.setFileSize(dto.getFileSize());
        entity.setUploadedAt(dto.getUploadedAt());

        // Set relations safely
        if (dto.getUploadedByUserId() != null) {
            User user = new User();
            user.setUserId(dto.getUploadedByUserId());
            entity.setUploadedByUser(user);
        }

        if (dto.getUploadedByCustomerId() != null) {
            Customer customer = new Customer();
            customer.setCustomerId(dto.getUploadedByCustomerId());
            entity.setUploadedByCustomer(customer);
        }

        return entity;
    }

}
