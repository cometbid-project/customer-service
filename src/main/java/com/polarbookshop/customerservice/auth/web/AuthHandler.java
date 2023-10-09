/**
 * 
 */
package com.polarbookshop.customerservice.auth.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.polarbookshop.customerservice.auth.errors.LoginDeniedException;
import com.polarbookshop.customerservice.auth.models.LoginRequest;
import com.polarbookshop.customerservice.auth.models.LoginResponse;
import com.polarbookshop.customerservice.auth.models.MFALoginRequest;
import com.polarbookshop.customerservice.auth.models.MFALoginResponse;
import com.polarbookshop.customerservice.auth.models.MFASignupResponse;
import com.polarbookshop.customerservice.auth.models.SignupRequest;
import com.polarbookshop.customerservice.auth.models.SignupResponse;
import com.polarbookshop.customerservice.auth.services.AuthService;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
@Component
@RequiredArgsConstructor
public class AuthHandler {

	private MediaType json = MediaType.APPLICATION_JSON;
	private final AuthService service;

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> signup(ServerRequest request) {
		Mono<SignupRequest> body = request.bodyToMono(SignupRequest.class);
		Mono<SignupResponse> result = body.flatMap(service::signup);

		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.onErrorResume(error -> ServerResponse.badRequest().build());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> login(ServerRequest request) {
		Mono<LoginRequest> body = request.bodyToMono(LoginRequest.class);
		Mono<LoginResponse> result = body.flatMap(service::login);

		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.switchIfEmpty(ServerResponse.notFound().build()).onErrorResume(error -> {
					if (error instanceof LoginDeniedException) {
						return ServerResponse.badRequest().build();
					}
					return ServerResponse.status(500).build();
				});
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> signupMFA(ServerRequest request) {
		Mono<SignupRequest> body = request.bodyToMono(SignupRequest.class);
		Mono<MFASignupResponse> result = body.flatMap(service::signupMFA);

		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.onErrorResume(error -> ServerResponse.badRequest().build());
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> loginMFA(ServerRequest request) {
		Mono<MFALoginRequest> body = request.bodyToMono(MFALoginRequest.class);
		Mono<MFALoginResponse> result = body.flatMap(service::loginMFA);

		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.switchIfEmpty(ServerResponse.notFound().build()).onErrorResume(error -> {
					if (error instanceof LoginDeniedException) {
						return ServerResponse.badRequest().build();
					}
					return ServerResponse.status(500).build();
				});
	}
}
