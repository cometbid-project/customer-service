/**
 * 
 */
package com.polarbookshop.customerservice.auth.models;

import lombok.Value;

/**
 * @author Gbenga
 *
 */
@Value
public class MFALoginRequest {
    
    String email;
    String password;
    String code;
}
