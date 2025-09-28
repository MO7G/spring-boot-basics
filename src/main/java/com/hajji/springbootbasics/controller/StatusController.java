package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.model.StandardStatus;
import com.hajji.springbootbasics.model.DocumentStatus;
import com.hajji.springbootbasics.repository.StandardStatusRepository;
import com.hajji.springbootbasics.repository.DocumentStatusRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    private final StandardStatusRepository standardStatusRepo;
    private final DocumentStatusRepository documentStatusRepo;

    public StatusController(StandardStatusRepository standardStatusRepo,
                            DocumentStatusRepository documentStatusRepo) {
        this.standardStatusRepo = standardStatusRepo;
        this.documentStatusRepo = documentStatusRepo;
    }

    @GetMapping("/standards")
    public List<StandardStatus> getAllStandardStatuses() {
        return standardStatusRepo.findAll();
    }

    @GetMapping("/documents")
    public List<DocumentStatus> getAllDocumentStatuses() {
        return documentStatusRepo.findAll();
    }
}
