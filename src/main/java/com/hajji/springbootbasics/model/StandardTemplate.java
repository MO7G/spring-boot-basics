package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "StandardTemplates")
public class StandardTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TemplateID")
    private Integer templateId;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "SectionID", nullable = false)
    private StandardSection section;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    // Getters & Setters
}
