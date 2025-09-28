package com.hajji.springbootbasics.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Integer roleId;

    @Column(name = "RoleName", nullable = false, unique = true, length = 100)
    private String roleName;

    @Column(name = "Description", length = 200)
    private String description;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt")
    private LocalDateTime modifiedAt;

    // 🔹 Relationships

    // Many-to-Many with Users (via UserRoleAssignments)
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    // Many-to-Many with Permissions (via RolePermissionMappings)
    @ManyToMany
    @JoinTable(
            name = "RolePermissionMappings",
            joinColumns = @JoinColumn(name = "RoleID"),
            inverseJoinColumns = @JoinColumn(name = "PermissionID")
    )
    private Set<Permission> permissions = new HashSet<>();

    // Constructors
    public Role() {}



}
