package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.dto.customer.UpdateCustomerRequestDTO;
import com.hajji.springbootbasics.model.Customer;

import java.time.LocalDateTime;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setModifiedAt(java.time.LocalDateTime.now());
        return customer;
    }

    public static CustomerResponseDTO toResponseDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setModifiedAt(customer.getModifiedAt());
        return dto;
    }


    // PATCH update logic — only mapper handles field resolution
    public static void updateEntity(Customer customer, UpdateCustomerRequestDTO dto) {
        if (customer == null || dto == null) return;

        dto.getName().ifProvided(customer::setName);
        dto.getEmail().ifProvided(customer::setEmail);

        customer.setModifiedAt(LocalDateTime.now());
    }

}
