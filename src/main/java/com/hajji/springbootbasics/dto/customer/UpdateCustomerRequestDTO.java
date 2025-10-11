package com.hajji.springbootbasics.dto.customer;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.hajji.springbootbasics.dto.patch.PatchField;

public class UpdateCustomerRequestDTO {

    private PatchField<Integer> customerId = PatchField.notProvided();
    private PatchField<String> name = PatchField.notProvided();
    private PatchField<String> email = PatchField.notProvided();

    @JsonSetter(nulls = Nulls.SET)
    public void setCustomerId(Integer customerId) {
        this.customerId = PatchField.of(customerId);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setName(String name) {
        this.name = PatchField.of(name);
    }

    @JsonSetter(nulls = Nulls.SET)
    public void setEmail(String email) {
        this.email = PatchField.of(email);
    }

    public PatchField<Integer> getCustomerId() {
        return customerId;
    }

    public PatchField<String> getName() {
        return name;
    }

    public PatchField<String> getEmail() {
        return email;
    }
}
