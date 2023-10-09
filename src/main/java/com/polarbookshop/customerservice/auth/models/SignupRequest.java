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
public class SignupRequest {

    private String email;
    private String password;
}
