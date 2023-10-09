/**
 * 
 */
package com.polarbookshop.customerservice.auth.models;

import lombok.Data;
import lombok.Value;

/**
 * @author Gbenga
 *
 */
@Value
public class LoginResponse {

   //private boolean success;
	private String userId;
	private String token;

}
