package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "ProjectAssignments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ProjectID", "UserID"})
        }
)
public class ProjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Integer assignmentId;

    @Column(name = "AssignedAt")
    private LocalDateTime assignedAt;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    /* ---------------- Relationships ---------------- */
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    // Many-to-Many between ProjectAssignments and Roles
    @ManyToMany
    @JoinTable(
            name = "ProjectRoles",
            joinColumns = @JoinColumn(name = "AssignmentID"),
            inverseJoinColumns = @JoinColumn(name = "RoleID")
    )
    private Set<Role> roles = new HashSet<>();

    // Getters & Setters
}
