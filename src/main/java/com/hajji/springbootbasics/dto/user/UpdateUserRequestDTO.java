package com.hajji.springbootbasics.dto.user;
import java.time.LocalDateTime;

import com.hajji.springbootbasics.dto.validation.user.CreateUser;
import com.hajji.springbootbasics.dto.validation.user.UpdateUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDTO {

    @NotBlank(groups = UpdateUser.class, message = "User ID is required for update")
    private Integer userId;

    @NotBlank(groups = CreateUser.class, message = "First name is required")
    @Size(max = 100, groups = {CreateUser.class, UpdateUser.class})
    private String firstName;

    @NotBlank(groups = CreateUser.class, message = "Last name is required")
    @Size(max = 100, groups = {CreateUser.class, UpdateUser.class})
    private String lastName;

    @NotBlank(groups = CreateUser.class, message = "Email is required")
    @Email(groups = {CreateUser.class, UpdateUser.class}, message = "Email must be valid")
    @Size(max = 200, groups = {CreateUser.class, UpdateUser.class})
    private String email;

    @NotBlank(groups = CreateUser.class, message = "Password is required")
    @Size(min = 10, max = 30, groups = CreateUser.class, message = "Password must be 10-30 characters")
    private String password;

    // Optional for updates, but you could require it on creation
    private Boolean isActive = true;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
