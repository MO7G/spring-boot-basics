package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.file.FileStorageResponseDTO;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.exceptions.fileStorage.InvalidFileTypeException;
import com.hajji.springbootbasics.exceptions.user.EmailAlreadyExistsException;
import com.hajji.springbootbasics.mapper.FileStorageMapper;
import com.hajji.springbootbasics.model.Customer;
import com.hajji.springbootbasics.model.FileStorage;
import com.hajji.springbootbasics.model.User;
import com.hajji.springbootbasics.repository.FileStorageRepository;
import com.hajji.springbootbasics.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final UserService userService;
    private final CustomerService customerService;

    public FileStorageService(FileStorageRepository fileStorageRepository,
                              UserService userService,
                              CustomerService customerService) {
        this.fileStorageRepository = fileStorageRepository;
        this.userService = userService;
        this.customerService = customerService;
    }

    @Transactional
    public FileStorage getFileById(Integer fileId) {
        return fileStorageRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with ID: " + fileId));
    }



    @Transactional
    public FileStorageResponseDTO uploadFile(MultipartFile file, Integer userId, Integer customerId) {
        // Validate exactly one uploader
        if ((userId == null && customerId == null) || (userId != null && customerId != null)) {
            throw new IllegalArgumentException("Exactly one of userId or customerId must be provided");
        }

        // Common validation for file
        if (file.isEmpty()) throw new RuntimeException("File is empty");
        if (file.getContentType() == null || !file.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new InvalidFileTypeException("Only PDF files are supported");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
            throw new InvalidFileTypeException("File must have a .pdf extension");
        }

        FileStorage entity = new FileStorage();
        entity.setFileName(fileName);
        entity.setFileSize(file.getSize());
        entity.setUploadedAt(LocalDateTime.now());
        entity.setFilePath("/uploads/" + fileName);

        // Set uploader dynamically
        if (userId != null) {
            User user = userService.getUserById(userId);
            entity.setUploadedByUser(user);
        } else {
            Customer customer = customerService.findCustomerById(customerId);
            entity.setUploadedByCustomer(customer);
        }

        FileStorage saved = fileStorageRepository.save(entity);
        return FileStorageMapper.toResponseDTO(saved);
    }


    @Transactional
    public void deleteFile(Integer fileId) {
        FileStorage file = getFileById(fileId); // helper method in FileStorageService

        // Optional: remove physical file from disk/cloud
        // Files.deleteIfExists(Paths.get(file.getFilePath()));

        fileStorageRepository.delete(file);
    }


}
