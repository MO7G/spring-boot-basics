package com.hajji.springbootbasics.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PermissionID")
    private Integer permissionId;

    @Column(name = "PermissionName", nullable = false, unique = true, length = 100)
    private String permissionName;

    @Column(name = "Description", length = 200)
    private String description;

    @Column(name = "CreatedAt" , insertable = false, updatable = false )
    @Generated(value= GenerationTime.INSERT)
    private LocalDateTime createdAt;

    @Column(name = "ModifiedAt" , insertable = false, updatable = false )
    @Generated(value = GenerationTime.ALWAYS)
    private LocalDateTime modifiedAt;

    /* ------------ Relationships ------------ */

    // Many-to-Many with Roles (via RolePermissionMappings)
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    /* ------------ Constructors ------------ */

    public Permission() {}

    /* ------------ Getters & Setters ------------ */

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}