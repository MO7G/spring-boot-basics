package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.dto.customer.UpdateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@Tag(name = "Customers", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Fetch customers with pagination
    @Operation(
            summary = "Get all customers with pagination",
            description = "Retrieves a paginated list of customers. Default page=0, size=20."
    )
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
    @Operation(
            summary = "Create a new customer",
            description = "Creates a new customer using the provided details."
    )
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

    // Update customer using patch
    @Operation(
            summary = "Update an existing customer",
            description = "Updates one or more fields of an existing customer record."
    )
    @PatchMapping("/update")
    public ResponseEntity<ApiResponseWrapper<CustomerResponseDTO>> applyGeneralUpdates(
            @Valid @RequestBody UpdateCustomerRequestDTO updateCustomerRequestDTO) {

        CustomerResponseDTO updatedCustomer = customerService.updateCustomer(updateCustomerRequestDTO);

        ApiResponseWrapper<CustomerResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Customer updated successfully",
                updatedCustomer
        );

        return ResponseEntity.ok(response);
    }

    // Delete a customer
    @Operation(
            summary = "Delete a customer",
            description = "Deletes a customer by ID. Optionally, set forceDelete=true to remove related records."
    )
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
