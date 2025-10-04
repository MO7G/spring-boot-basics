package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customers" , schema = "dbo")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Integer customerId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Email", unique = true, length = 200)
    private String email;

    @Column(name = "CreatedAt" , insertable = false, updatable = false)
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime createdAt;


    @OneToMany(mappedBy = "uploadedByCustomer")
    private Set<FileStorage> uploadedFilesByCustomer = new HashSet<>();


    @Column(name = "ModifiedAt" , insertable = false, updatable = false)
    @Generated(value = GenerationTime.ALWAYS)
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @OneToMany(mappedBy = "customer")
    private Set<Project> projects = new HashSet<>();


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<FileStorage> getUploadedFilesByCustomer() {
        return uploadedFilesByCustomer;
    }

    public void setUploadedFilesByCustomer(Set<FileStorage> uploadedFilesByCustomer) {
        this.uploadedFilesByCustomer = uploadedFilesByCustomer;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
