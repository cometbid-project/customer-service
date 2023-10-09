/**
 * 
 */
package com.polarbookshop.customerservice.auth.models;

import lombok.Value;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Gbenga
 *
 */
@Value
@Document(collection = "mfa_users")
public class MFAUser extends User {

	String secretKey;

	public MFAUser(String userId, String email, String hash, String salt, String secret) {
		super(userId, email, hash, salt, Set.of());
		this.secretKey = secret;
	}

	public MFAUser() {
		this(null, null, null, null, null);
	}

}