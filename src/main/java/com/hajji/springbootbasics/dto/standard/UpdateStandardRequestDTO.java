package com.hajji.springbootbasics.dto.standard;

import com.hajji.springbootbasics.dto.patch.PatchField;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import java.time.LocalDate;

public class UpdateStandardRequestDTO {

    private PatchField<Integer> standardId = PatchField.notProvided();
    private PatchField<String> name = PatchField.notProvided();
    private PatchField<String> version = PatchField.notProvided();


    @JsonSetter(nulls = Nulls.SET)
    public void setStandardId(Integer standardId) {
        this.standardId = PatchField.of(standardId);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setName(String name) {
        this.name = PatchField.of(name);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setVersion(String version) {
        this.version = PatchField.of(version);
    }


    // Getters
    public PatchField<Integer> getStandardId() { return standardId; }
    public PatchField<String> getName() { return name; }
    public PatchField<String> getVersion() { return version; }
}
