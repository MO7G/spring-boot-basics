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
}
