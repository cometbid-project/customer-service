/**
 * 
 */
package com.polarbookshop.customerservice.auth.models;

import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gbenga
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	protected String userId;
	protected String email;
	protected String hash;
	protected String salt;

	protected Set<Role> roles;

	Set<Role> getRoles() {
		return Set.copyOf(roles);
	}
}
