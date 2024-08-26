package com.micro.ecommerce.customer;

import com.micro.ecommerce.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = customerRepository.save(customerMapper.toCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id %s was not found".formatted(request.id())));
        customer.setAddress(request.address());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customerRepository.save(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(String id) {
        return customerRepository
                .findById(id)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id %s was not found".formatted(id)));
    }

    public boolean existsById(String id) {
        return customerRepository.existsById(id);
    }

    public void deleteById(String id) {
        if (!customerRepository.existsById(id)) throw new CustomerNotFoundException("Customer with id %s was not found".formatted(id));
        customerRepository.deleteById(id);
    }
}
