package com.hajji.springbootbasics.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FileStorage")
public class FileStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FileID")
    private Integer fileId;


    @Column(name = "FileName", nullable = false, length = 200)
    private String fileName;

    @Column(name = "FilePath", nullable = false, length = 500)
    private String filePath;

    @Column(name = "FileSize")
    private Long fileSize;

    @Column(name = "UploadedAt")
    private LocalDateTime uploadedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "UploadedBy", nullable = false)
    private User uploadedBy;


    @OneToMany(mappedBy = "file")
    private Set<StandardTemplate> standardTemplates = new HashSet<>();


    // One FileStorage may be referenced in many project documents
    @OneToMany(mappedBy = "file")
    private Set<ProjectDocument> projectDocuments = new HashSet<>();

    // One FileStorage may be referenced in many revisions
    @OneToMany(mappedBy = "file")
    private Set<DocumentRevision> documentRevisions = new HashSet<>();


}
