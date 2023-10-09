/**
 * 
 */
package com.polarbookshop.customerservice.auth.managers;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
public interface TokenManager {

	String issueToken(String userId);

	Mono<String> parse(String token);

}
