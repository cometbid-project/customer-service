/**
 * 
 */
package com.polarbookshop.customerservice.services;

import lombok.Value;
import org.springframework.stereotype.Component;

import com.polarbookshop.customerservice.models.CustomerModel;
import com.polarbookshop.customerservice.repositories.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
@Component("CustomerService")
@Value
public class CustomerServiceImpl implements CustomerService {
    
    CustomerRepository customerRepository;

    @Override
    public Mono<CustomerModel> createCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<CustomerModel> updateCustomer(CustomerModel customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Void> removeCustomer(String customerId) {
        return customerRepository.deleteById(customerId);
    }

    @Override
    public Mono<CustomerModel> findCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Flux<CustomerModel> findAllCustomers() {
        return customerRepository.findAll();
    }
}

