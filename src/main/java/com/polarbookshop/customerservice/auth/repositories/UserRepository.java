/**
 * 
 */
package com.polarbookshop.customerservice.auth.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.polarbookshop.customerservice.auth.models.User;
import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
public interface UserRepository extends ReactiveMongoRepository<User, String>, CustomUserRepository {

    Mono<User> findByEmail (String email);

}
