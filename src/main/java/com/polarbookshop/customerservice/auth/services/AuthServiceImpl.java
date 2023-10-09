/**
 * 
 */
package com.polarbookshop.customerservice.auth.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.polarbookshop.customerservice.auth.errors.AlreadyExistsException;
import com.polarbookshop.customerservice.auth.errors.LoginDeniedException;
import com.polarbookshop.customerservice.auth.errors.UserNotFoundException;
import com.polarbookshop.customerservice.auth.managers.TokenManager;
import com.polarbookshop.customerservice.auth.managers.TotpManager;
import com.polarbookshop.customerservice.auth.models.LoginRequest;
import com.polarbookshop.customerservice.auth.models.LoginResponse;
import com.polarbookshop.customerservice.auth.models.MFALoginRequest;
import com.polarbookshop.customerservice.auth.models.MFALoginResponse;
import com.polarbookshop.customerservice.auth.models.MFASignupResponse;
import com.polarbookshop.customerservice.auth.models.MFAUser;
import com.polarbookshop.customerservice.auth.models.Role;
import com.polarbookshop.customerservice.auth.models.SignupRequest;
import com.polarbookshop.customerservice.auth.models.SignupResponse;
import com.polarbookshop.customerservice.auth.models.User;
import com.polarbookshop.customerservice.auth.repositories.UserRepository;

import reactor.core.publisher.Mono;
import lombok.Value;

/**
 * @author Gbenga
 *
 */
@Component("AuthService")
@Value
public class AuthServiceImpl implements AuthService {

	TokenManager tokenManager;
	TotpManager totpManager;
	UserRepository repository;

	@Override
	public Mono<SignupResponse> signup(SignupRequest request) {
		// generating a new user entity params
		// step 1
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String salt = BCrypt.gensalt();
		String hash = BCrypt.hashpw(password, salt);
		Role userRole = Role.createUserRole();
		// String secret = totpManager.generateSecret();
		User user = new User(null, email, hash, salt, List.of(userRole));

		// preparing a Mono
		Mono<SignupResponse> response = repository.findByEmail(email)
				// step 2
				.defaultIfEmpty(user).flatMap(result -> {
					// assert, that user does not exist
					// step 3
					if (result.getUserId() == null) {
						// step 4
						return repository.save(result).flatMap(result2 -> {
							// step 5
							String userId = result2.getUserId();
							SignupResponse signupResponse = new SignupResponse(userId);
							return Mono.just(signupResponse);
						});
					} else {
						// step 6
						// scenario - user already exists
						return Mono.error(new AlreadyExistsException());
					}
				});

		return response;
	}

	@Override
	public Mono<LoginResponse> login(LoginRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String code = request.getCode();
		Mono<LoginResponse> response = repository.findByEmail(email)
				// step 1
				.defaultIfEmpty(new User()).flatMap(user -> {
					// step 2
					if (user.getUserId() == null) {
						// no user
						return Mono.empty();
					} else {
						// step 3: user exists
						String salt = user.getSalt();
						// String secret = user.getSecretKey();
						boolean passwordMatch = BCrypt.hashpw(password, salt).equalsIgnoreCase(user.getHash());
						// step 4: check if password matched
						if (passwordMatch) {							
							String token = tokenManager.issueToken(user.getUserId());
							LoginResponse loginResponse = new LoginResponse(user.getUserId(), token);
							return Mono.just(loginResponse);
						} else {
							return Mono.error(new LoginDeniedException());
						}
					}
				});
		return response;
	}

	@Override
	public Mono<String> parseToken(String token) {
		return tokenManager.parse(token);
	}

	@Override
	public Mono<MFASignupResponse> signupMFA(SignupRequest request) {
		// generating a new user entity params
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String salt = BCrypt.gensalt();
		String hash = BCrypt.hashpw(password, salt);
		String secret = totpManager.generateSecret();

		MFAUser user = new MFAUser(null, email, hash, salt, secret);

		// preparing a Mono
		Mono<MFASignupResponse> response = repository.findByEmail(email)
				// step 2
				.defaultIfEmpty(user).flatMap(result -> {
					// assert, that user does not exist
					if (result.getUserId() == null) {
						return repository.save(result).flatMap(result2 -> {
							// prepare token
							String userId = result2.getUserId();
							MFASignupResponse signupResponse = new MFASignupResponse(userId, secret);
							return Mono.just(signupResponse);
						});
					} else {
						// scenario - user already exists
						return Mono.error(new AlreadyExistsException());
					}
				});
		return response;
	}

	@Override
	public Mono<MFALoginResponse> loginMFA(MFALoginRequest request) {
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String code = request.getCode();

		Mono<MFALoginResponse> response = repository.findByEmail(email)
				// step 1
				.defaultIfEmpty(new MFAUser()).flatMap(user -> {
					// step 2
					if (user.getUserId() == null) {
						// no user
						return Mono.empty();
					} else {
						MFAUser mfaUser = (MFAUser) user;
						// user exists
						String salt = mfaUser.getSalt();
						String secret = mfaUser.getSecretKey();
						boolean passwordMatch = BCrypt.hashpw(password, salt).equalsIgnoreCase(mfaUser.getHash());

						if (passwordMatch) {
							// step 4: password matched
							boolean codeMatched = totpManager.validateCode(code, secret);

							if (codeMatched) {
								// step 5
								String token = tokenManager.issueToken(mfaUser.getUserId());
								MFALoginResponse loginResponse = new MFALoginResponse(mfaUser.getUserId(), token);
								return Mono.just(loginResponse);
							} else {
								return Mono.error(new LoginDeniedException());
							}
						} else {
							return Mono.error(new UserNotFoundException());
						}
					}
				});

		return response;
	}
}
