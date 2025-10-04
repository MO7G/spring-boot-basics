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
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers(page, size);
=======
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers();
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca

        ApiResponse<List<CustomerResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Customers fetched successfully",
                customers
        );

        return ResponseEntity.ok(response);
    }

<<<<<<< HEAD

=======
>>>>>>> 637e1fb2e48e5bc37715b4033bf3615469a047ca
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
