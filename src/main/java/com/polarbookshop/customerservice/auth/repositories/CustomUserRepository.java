/**
 * 
 */
package com.polarbookshop.customerservice.auth.repositories;

import com.polarbookshop.customerservice.auth.models.Role;
import com.polarbookshop.customerservice.auth.models.User;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
public interface CustomUserRepository {
	
	Mono<User> changePassword(String userId, String newPassword);

	Mono<User> addNewRole(String userId, Role role);

	Mono<User> removeRole(String userId, String permission);

	Mono<Boolean> hasPermission(String userId, String permission);
}