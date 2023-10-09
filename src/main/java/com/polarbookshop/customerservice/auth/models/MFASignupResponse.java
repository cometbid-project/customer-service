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
public class MFASignupResponse {
    
    String userId;
    String secretKey;
}
