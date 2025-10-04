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
    @JoinColumn(name = "UploadedByUserID", nullable = true)
    private User uploadedByUser;


    @ManyToOne
    @JoinColumn(name = "UploadedByCustomerID", nullable = true)
    private Customer uploadedByCustomer;


    @OneToMany(mappedBy = "file")
    private Set<StandardTemplate> standardTemplates = new HashSet<>();


    // One FileStorage may be referenced in many project documents
    @OneToMany(mappedBy = "file")
    private Set<ProjectDocument> projectDocuments = new HashSet<>();

    // One FileStorage may be referenced in many revisions
    @OneToMany(mappedBy = "file")
    private Set<DocumentRevision> documentRevisions = new HashSet<>();


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

    public User getUploadedByUser() {
        return uploadedByUser;
    }

    public void setUploadedByUser(User uploadedByUser) {
        this.uploadedByUser = uploadedByUser;
    }

    public Customer getUploadedByCustomer() {
        return uploadedByCustomer;
    }

    public void setUploadedByCustomer(Customer uploadedByCustomer) {
        this.uploadedByCustomer = uploadedByCustomer;
    }

    public Set<StandardTemplate> getStandardTemplates() {
        return standardTemplates;
    }

    public void setStandardTemplates(Set<StandardTemplate> standardTemplates) {
        this.standardTemplates = standardTemplates;
    }

    public Set<ProjectDocument> getProjectDocuments() {
        return projectDocuments;
    }

    public void setProjectDocuments(Set<ProjectDocument> projectDocuments) {
        this.projectDocuments = projectDocuments;
    }

    public Set<DocumentRevision> getDocumentRevisions() {
        return documentRevisions;
    }

    public void setDocumentRevisions(Set<DocumentRevision> documentRevisions) {
        this.documentRevisions = documentRevisions;
    }
}
