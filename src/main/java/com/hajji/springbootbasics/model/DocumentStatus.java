package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DocumentStatuses")
public class DocumentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private Integer statusId;

    @Column(name = "StatusName", nullable = false, unique = true, length = 50)
    private String statusName;

    @Column(name = "Description", length = 200)
    private String description;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @OneToMany(mappedBy = "status")
    private Set<ProjectDocument> projectDocuments = new HashSet<>();

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<ProjectDocument> getProjectDocuments() {
        return projectDocuments;
    }

    public void setProjectDocuments(Set<ProjectDocument> projectDocuments) {
        this.projectDocuments = projectDocuments;
    }
}
