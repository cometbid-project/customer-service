/**
 * 
 */
package com.polarbookshop.customerservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.polarbookshop.customerservice.models.CustomerModel;

/**
 * @author Gbenga
 *
 */
public interface CustomerRepository extends ReactiveMongoRepository<CustomerModel, String> {
    
}
