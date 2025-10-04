package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ProjectStatus")
public class ProjectStatus {

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
    private Set<Project> projects = new HashSet<>();

    // Getters & Setters

    public Integer getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
