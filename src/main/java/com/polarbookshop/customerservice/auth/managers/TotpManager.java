/**
 * 
 */
package com.polarbookshop.customerservice.auth.managers;

/**
 * @author Gbenga
 *
 */
public interface TotpManager {

    String generateSecret ();

    boolean validateCode (String code, String secret);

}
