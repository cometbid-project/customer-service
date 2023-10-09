/**
 * 
 */
package com.polarbookshop.customerservice.auth.web;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
@Component
class ProtectedHandler {

	private MediaType json = MediaType.APPLICATION_JSON;

	public Mono<ServerResponse> sayHello(ServerRequest request) {
		return ServerResponse.ok().contentType(json).bodyValue("Hello, protected endpoint!");
	}

}
