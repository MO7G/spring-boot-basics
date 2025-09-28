package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Integer projectId;

    @Column(name = "StartDate")
    private LocalDateTime startDate;

    @Column(name = "CompletionDate")
    private LocalDateTime completionDate;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "StandardID", nullable = false)
    private Standard standard;

    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private StandardStatus status;

    @OneToMany(mappedBy = "project")
    private Set<ProjectDocument> projectDocuments = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<ProjectAssignment> assignments = new HashSet<>();

    // Getters & Setters
}
