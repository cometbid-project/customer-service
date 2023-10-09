/**
 * 
 */
package com.polarbookshop.customerservice.auth.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author Gbenga
 *
 */
@Value
@AllArgsConstructor
@Document(collection = "permissions")
public class Role {

	@Id
	String id;
	Permission permission;

	private Role() {
		this.id = null;
		this.permission = null;
	}

	public static Role createUserRole() {
		String id = UUID.randomUUID().toString();
		return new Role(id, Permission.USER);
	}
	
	enum Permission {
		USER,
		ADMIN;
	}
}
