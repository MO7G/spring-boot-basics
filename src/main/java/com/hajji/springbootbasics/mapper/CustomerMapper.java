package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.model.Customer;

public class CustomerMapper {

    public static Customer toEntity(CreateCustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setCreatedAt(java.time.LocalDateTime.now());
        customer.setModifiedAt(java.time.LocalDateTime.now());
        return customer;
    }

    public static CustomerResponseDTO toDTO(Customer customer) {
        CustomerResponseDTO dto = new CustomerResponseDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setModifiedAt(customer.getModifiedAt());
        return dto;
    }
}
