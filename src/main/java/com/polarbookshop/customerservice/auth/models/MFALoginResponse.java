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
public class MFALoginResponse {

  // private boolean success;
  private String userId;
  private String token;
}

