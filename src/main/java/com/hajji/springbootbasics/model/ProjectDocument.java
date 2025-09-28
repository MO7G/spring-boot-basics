package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ProjectDocuments")
public class ProjectDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectDocumentID")
    private Integer projectDocumentId;

    @Column(name = "VersionNumber")
    private Integer versionNumber;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "TemplateID", nullable = false)
    private StandardTemplate template;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private DocumentStatus status;

    @ManyToOne
    @JoinColumn(name = "LastModifiedBy")
    private User lastModifiedBy;

    @OneToMany(mappedBy = "projectDocument")
    private Set<DocumentRevision> revisions = new HashSet<>();

    // Getters & Setters
}
