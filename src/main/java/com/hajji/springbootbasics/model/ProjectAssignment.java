package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "ProjectAssignments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ProjectID", "UserID"})
        }
)
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Integer assignmentId;

    @Column(name = "AssignedAt")
    private LocalDateTime assignedAt;

    @Column(name = "CreatedAt" , insertable = false, updatable = false)
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt" ,insertable = false, updatable = false)
    @Generated(value = GenerationTime.ALWAYS)
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    // Many-to-Many between ProjectAssignments and Roles
    @ManyToMany
    @JoinTable(
            name = "ProjectRoles",
            joinColumns = @JoinColumn(name = "AssignmentID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Set<Role> roles = new HashSet<>();

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
