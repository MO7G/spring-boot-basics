package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.customer.CreateCustomerRequestDTO;
import com.hajji.springbootbasics.dto.customer.CustomerResponseDTO;
import com.hajji.springbootbasics.exceptions.common.ResourceNotFoundException;
import com.hajji.springbootbasics.mapper.CustomerMapper;
import com.hajji.springbootbasics.model.Customer;
import com.hajji.springbootbasics.model.Project;
import com.hajji.springbootbasics.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO dto) {
        // Optional: check if email already exists
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Customer with this email already exists");
        }

        Customer customer = CustomerMapper.toEntity(dto);
        customer = customerRepository.save(customer);
        return CustomerMapper.toDTO(customer);
    }


    @Transactional
    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper::toDTO)
                .toList();
    }



    @Transactional
    public void deleteCustomer(Integer customerId, boolean forceDelete) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        Set<Project> projects = customer.getProjects();

        if (!projects.isEmpty()) {
            if (forceDelete) {
                // Cascade delete: remove all linked projects
                for (Project project : projects) {
                    project.setCustomer(null); // optional: could delete projectRepository.delete(project) if business allows
                }
                projects.clear();
            } else {
                // Prevent deletion
                throw new IllegalStateException(
                        "Cannot delete customer with linked projects. " +
                                "Use forceDelete=true to remove them."
                );
            }
        }

        // Delete customer safely
        customerRepository.delete(customer);
    }


    @Transactional
    public Customer findCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new ResourceNotFoundException("Customer not found with ID: " + customerId)
        );

        return customer;
    }

}
