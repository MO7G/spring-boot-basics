package com.hajji.springbootbasics.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "FirstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "PasswordHash", nullable = false, length = 500)
    private String passwordHash;

    @Column(name = "IsActive")
    private Boolean isActive = true;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /*--------------Relationships---------------*/


    // Many Users <-> Many Roles (via UserRoleAssignments)
    @ManyToMany
    @JoinTable(
            name = "UserRoleAssignments",
            joinColumns = @JoinColumn(name = "UserID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Set<Role> roles = new HashSet<>();


    // One User -> Many FileStorage (uploaded files)
    @OneToMany(mappedBy = "uploadedBy")
    private Set<FileStorage> uploadedFiles = new HashSet<>();

    // One User -> Many ProjectAssignments
    @OneToMany(mappedBy = "user")
    private Set<ProjectAssignment> projectAssignments = new HashSet<>();

    // One User -> Many ProjectDocuments (last modified by)
    @OneToMany(mappedBy = "lastModifiedBy")
    private Set<ProjectDocument> modifiedDocuments = new HashSet<>();

    // One User -> Many DocumentRevisions (modified by)
    @OneToMany(mappedBy = "modifiedBy")
    private Set<DocumentRevision> documentRevisions = new HashSet<>();


    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public Boolean getIsActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<FileStorage> getUploadedFiles() {
        return uploadedFiles;
    }

    public Set<ProjectAssignment> getProjectAssignments() {
        return projectAssignments;
    }

    public Set<ProjectDocument> getModifiedDocuments() {
        return modifiedDocuments;
    }

    public Set<DocumentRevision> getDocumentRevisions() {
        return documentRevisions;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUploadedFiles(Set<FileStorage> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public void setProjectAssignments(Set<ProjectAssignment> projectAssignments) {
        this.projectAssignments = projectAssignments;
    }

    public void setModifiedDocuments(Set<ProjectDocument> modifiedDocuments) {
        this.modifiedDocuments = modifiedDocuments;
    }

    public void setDocumentRevisions(Set<DocumentRevision> documentRevisions) {
        this.documentRevisions = documentRevisions;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", roles=" + roles +
                ", uploadedFiles=" + uploadedFiles +
                ", projectAssignments=" + projectAssignments +
                ", modifiedDocuments=" + modifiedDocuments +
                ", documentRevisions=" + documentRevisions +
                '}';
    }
}
