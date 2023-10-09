/**
 * 
 */
package com.polarbookshop.customerservice.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;
import com.polarbookshop.customerservice.auth.models.LoginRequest;
import com.polarbookshop.customerservice.auth.repositories.UserRepository;
import com.polarbookshop.customerservice.auth.services.AuthServiceImpl;
import com.polarbookshop.customerservice.repositories.CustomerRepository;
import com.polarbookshop.customerservice.services.CustomerServiceImpl;

import reactor.test.StepVerifier;

/**
 * @author Gbenga
 *
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	@Mock
	private UserRepository repository;

	@InjectMocks
	private AuthServiceImpl service;

	@Test
	void findUserByEmailTest() {
		final String email = "jana.novakova@seznam.cz";
		final String password = "";
		
		LoginRequest loginRequest = new LoginRequest(); 
		loginRequest.setEmail(email);
		loginRequest.setPassword(password); 

		StepVerifier.create(service.login(loginRequest))
				//
				.assertNext(result -> Assertions.assertThat(result)
						//
						.isNotNull())
				.verifyComplete();
	}
}
