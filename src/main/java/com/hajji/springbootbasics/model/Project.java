package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Integer projectId;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "CompletionDate")
    private LocalDateTime completionDate;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "StandardID", nullable = false)
    private Standard standard;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private ProjectStatus status;

    @OneToMany(mappedBy = "project")
    private Set<ProjectDocument> projectDocuments = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<ProjectAssignment> assignments = new HashSet<>();

    // Getters & Setters

    public Integer getProjectId() {
        return projectId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Standard getStandard() {
        return standard;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public Set<ProjectDocument> getProjectDocuments() {
        return projectDocuments;
    }

    public Set<ProjectAssignment> getAssignments() {
        return assignments;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public void setProjectDocuments(Set<ProjectDocument> projectDocuments) {
        this.projectDocuments = projectDocuments;
    }

    public void setAssignments(Set<ProjectAssignment> assignments) {
        this.assignments = assignments;
    }
}
