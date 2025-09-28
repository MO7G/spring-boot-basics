package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DocumentRevisions")
public class DocumentRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RevisionID")
    private Integer revisionId;

    @Column(name = "VersionNumber", nullable = false)
    private Integer versionNumber;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    @Column(name = "ChangeNote", length = 500)
    private String changeNote;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectDocumentID", nullable = false)
    private ProjectDocument projectDocument;

    @ManyToOne
    @JoinColumn(name = "FileID", nullable = false)
    private FileStorage file;

    @ManyToOne
    @JoinColumn(name = "ModifiedBy", nullable = false)
    private User modifiedBy;

    // Getters & Setters
}
