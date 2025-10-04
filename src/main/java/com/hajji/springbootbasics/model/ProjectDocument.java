package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ProjectDocuments")
public class ProjectDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectDocumentID")
    private Integer projectDocumentId;

    @Column(name = "VersionNumber")
    private Integer versionNumber;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "TemplateID", nullable = false)
    private StandardTemplate template;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private DocumentStatus status;

    @ManyToOne
    @JoinColumn(name = "LastModifiedBy")
    private User lastModifiedBy;

    @OneToMany(mappedBy = "projectDocument")
    private Set<DocumentRevision> revisions = new HashSet<>();

    public Integer getProjectDocumentId() {
        return projectDocumentId;
    }

    public void setProjectDocumentId(Integer projectDocumentId) {
        this.projectDocumentId = projectDocumentId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public StandardTemplate getTemplate() {
        return template;
    }

    public void setTemplate(StandardTemplate template) {
        this.template = template;
    }

    public FileStorage getFile() {
        return file;
    }

    public void setFile(FileStorage file) {
        this.file = file;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<DocumentRevision> getRevisions() {
        return revisions;
    }

    public void setRevisions(Set<DocumentRevision> revisions) {
        this.revisions = revisions;
    }
}
