package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.dto.response.ApiResponse;
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


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();

        ApiResponse<List<CustomerResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customers fetched successfully",
                customers
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CreateCustomerRequestDTO requestDTO) {

        CustomerResponseDTO customer = customerService.createCustomer(requestDTO);

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Customer created successfully",
                customer
        );

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("delete/{customerId}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(
            @PathVariable Integer customerId,
            @RequestParam(defaultValue = "false") boolean forceDelete) {

        customerService.deleteCustomer(customerId, forceDelete);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customer deleted successfully",
                "Customer ID: " + customerId
        );

        return ResponseEntity.ok(response);
    }
}
