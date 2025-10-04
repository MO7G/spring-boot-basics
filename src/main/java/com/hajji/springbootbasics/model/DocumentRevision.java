package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DocumentRevisions")
public class DocumentRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RevisionID")
    private Integer revisionId;

    @Column(name = "VersionNumber", nullable = false)
    private Integer versionNumber;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;


    @Column(name = "ChangeNote", length = 500)
    private String changeNote;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectDocumentID", nullable = false)
    private ProjectDocument projectDocument;


    @ManyToOne
    @JoinColumn(name = "ModifiedByUserID" , nullable = true)
    private User modifiedByUserId;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    public Integer getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(Integer revisionId) {
        this.revisionId = revisionId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getChangeNote() {
        return changeNote;
    }

    public void setChangeNote(String changeNote) {
        this.changeNote = changeNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ProjectDocument getProjectDocument() {
        return projectDocument;
    }

    public void setProjectDocument(ProjectDocument projectDocument) {
        this.projectDocument = projectDocument;
    }

    public User getModifiedByUserId() {
        return modifiedByUserId;
    }

    public void setModifiedByUserId(User modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }

    public FileStorage getFile() {
        return file;
    }

    public void setFile(FileStorage file) {
        this.file = file;
    }

// Getters & Setters
}
