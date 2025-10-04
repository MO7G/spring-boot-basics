package com.hajji.springbootbasics.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

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

    @Column(name = "CreatedAt" , insertable = false, updatable = false )
    @Generated(value = GenerationTime.INSERT)
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt" , insertable = false , updatable = false)
    @Generated(value = GenerationTime.ALWAYS)
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

    public Integer getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
