/**
 * 
 */
package com.polarbookshop.customerservice.auth.services;

import com.polarbookshop.customerservice.auth.models.LoginRequest;
import com.polarbookshop.customerservice.auth.models.LoginResponse;
import com.polarbookshop.customerservice.auth.models.MFALoginRequest;
import com.polarbookshop.customerservice.auth.models.MFALoginResponse;
import com.polarbookshop.customerservice.auth.models.MFASignupResponse;
import com.polarbookshop.customerservice.auth.models.SignupRequest;
import com.polarbookshop.customerservice.auth.models.SignupResponse;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
public interface AuthService {
	
	Mono<SignupResponse> signup(SignupRequest request);

	Mono<LoginResponse> login(LoginRequest request);

	Mono<MFASignupResponse> signupMFA(SignupRequest request);

	Mono<MFALoginResponse> loginMFA(MFALoginRequest request);

	Mono<String> parseToken(String token);
}
