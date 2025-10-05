package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer/")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Fetch customers with pagination
    @GetMapping("/all")
    public ResponseEntity<ApiResponseWrapper<List<CustomerResponseDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers(page, size);

        ApiResponseWrapper<List<CustomerResponseDTO>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Customers fetched successfully",
                customers
        );

        return ResponseEntity.ok(response);
    }

    // Create a new customer
    @PostMapping("/create")
    public ResponseEntity<ApiResponseWrapper<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CreateCustomerRequestDTO requestDTO) {

        CustomerResponseDTO customer = customerService.createCustomer(requestDTO);

        ApiResponseWrapper<CustomerResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }

    // Delete a customer
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<ApiResponseWrapper<String>> deleteCustomer(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "false") boolean forceDelete) {

        customerService.deleteCustomer(customerId, forceDelete);

        ApiResponseWrapper<String> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Customer deleted successfully",
                "Customer ID: " + customerId
        );

        return ResponseEntity.ok(response);
    }
}
