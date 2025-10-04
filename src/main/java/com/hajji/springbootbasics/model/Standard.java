package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Standards")
public class Standard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StandardID")
    private Integer standardId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Version", length = 50)
    private String version;

    @Column(name = "PublishedDate")
    private LocalDate publishedDate;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @OneToMany(mappedBy = "standard")
    private Set<StandardSection> sections = new HashSet<>();

    @OneToMany(mappedBy = "standard")
    private Set<Project> projects = new HashSet<>();

    // Getters & Setters

    public Integer getStandardId() {
        return standardId;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Set<StandardSection> getSections() {
        return sections;
    }

    public Set<Project> getProjects() {
        return projects;
    }


    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setSections(Set<StandardSection> sections) {
        this.sections = sections;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
