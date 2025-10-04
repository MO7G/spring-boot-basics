package com.hajji.springbootbasics.dto.user;

import com.hajji.springbootbasics.dto.patch.PatchField;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDateTime;

public class UpdateUserRequestDTO {

    private PatchField<Integer> userId = PatchField.notProvided();
    private PatchField<String> firstName = PatchField.notProvided();
    private PatchField<String> lastName = PatchField.notProvided();
    private PatchField<Boolean> isActive = PatchField.notProvided();

    @JsonSetter(nulls = Nulls.SET)
    public void setUserId(Integer userId) {
        this.userId = PatchField.of(userId);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setFirstName(String firstName) {
        this.firstName = PatchField.of(firstName);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setLastName(String lastName) {
        this.lastName = PatchField.of(lastName);
    }


    @JsonSetter(nulls = Nulls.SET)
    public void setIsActive(Boolean isActive) {
        this.isActive = PatchField.of(isActive);
    }

    public PatchField<Integer> getUserId() { return userId; }
    public PatchField<String> getFirstName() { return firstName; }
    public PatchField<String> getLastName() { return lastName; }
    public PatchField<Boolean> getIsActive() { return isActive; }


}
