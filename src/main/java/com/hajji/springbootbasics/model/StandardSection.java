package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "StandardSections")
public class StandardSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SectionID")
    private Integer sectionId;

    @Column(name = "Number", nullable = false, length = 50)
    private String number;

    @Column(name = "Title", nullable = false, length = 200)
    private String title;

    @Column(name = "OrderIndex")
    private Integer orderIndex;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "StandardID", nullable = false)
    private Standard standard;

    @ManyToOne
    @JoinColumn(name = "ParentSectionID")
    private StandardSection parentSection;

    @OneToMany(mappedBy = "parentSection")
    private Set<StandardSection> childSections = new HashSet<>();

    @OneToMany(mappedBy = "section")
    private Set<StandardTemplate> templates = new HashSet<>();

    // Getters & Setters

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public StandardSection getParentSection() {
        return parentSection;
    }

    public void setParentSection(StandardSection parentSection) {
        this.parentSection = parentSection;
    }

    public Set<StandardSection> getChildSections() {
        return childSections;
    }

    public void setChildSections(Set<StandardSection> childSections) {
        this.childSections = childSections;
    }

    public Set<StandardTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<StandardTemplate> templates) {
        this.templates = templates;
    }
}
