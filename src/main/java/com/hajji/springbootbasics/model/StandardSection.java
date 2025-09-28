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
}
