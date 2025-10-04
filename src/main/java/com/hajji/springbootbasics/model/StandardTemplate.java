package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;

@Entity
@Table(name = "StandardTemplates")
public class StandardTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TemplateID")
    private Integer templateId;

    @Column(name = "CreatedAt",insertable = false,updatable = false)
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt" ,insertable=false , updatable=false )
    @Generated(value = GenerationTime.ALWAYS)
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "SectionID", nullable = false)
    private StandardSection section;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    // Getters & Setters

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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

    public StandardSection getSection() {
        return section;
    }

    public void setSection(StandardSection section) {
        this.section = section;
    }

    public FileStorage getFile() {
        return file;
    }

    public void setFile(FileStorage file) {
        this.file = file;
    }
}
