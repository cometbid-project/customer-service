/**
 * 
 */
package com.polarbookshop.customerservice.user;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.polarbookshop.customerservice.models.CustomerModel;
import com.polarbookshop.customerservice.repositories.CustomerRepository;
import com.polarbookshop.customerservice.services.CustomerServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author Gbenga
 *
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository repository;

	@InjectMocks
	private CustomerServiceImpl service;

	@Test
	void createCustomerTest() {
		final CustomerModel customer = new CustomerModel("customerId", "Acme s.r.o", "jana.dvorakova@acme.cz",
				"CZ1234567", null, null);

		Mockito.when(repository.save(customer)).thenReturn(Mono.just(customer));
		StepVerifier.create(service.createCustomer(customer))
				//
				.assertNext(result -> Assertions.assertThat(result)
						//
						.isEqualTo(customer))
				.verifyComplete();
	}

	@Test
	void findCustomerByIdSuccessTest() {
		final CustomerModel customer = new CustomerModel("customerId", "Acme s.r.o", "jana.dvorakova@acme.cz",
				"CZ1234567", null, null);

		final String customerId = customer.getCustomerId();

		Mockito.when(repository.findById(customerId)).thenReturn(Mono.just(customer));
		StepVerifier.create(service.findCustomerById(customerId))
				//
				.assertNext(result -> Assertions.assertThat(result)
						//
						.isEqualTo(customer))
				.verifyComplete();
	}

	@Test
	void findCustomerByIdNotFoundTest() {
		final String customerId = "customerId";

		Mockito.when(repository.findById(customerId)).thenReturn(Mono.empty());
		StepVerifier.create(service.findCustomerById(customerId)).verifyComplete();
	}

	@Test
	void findAllCustomersTest() {
		List<CustomerModel> customers = List.of(
				new CustomerModel("customerId1", "Acme s.r.o", "jana.dvorakova@acme.cz", "CZ1234567", null, null),
				new CustomerModel("customerId2", "Acme s.r.o", "jana.dvorakova@acme.cz", "CZ1234567", null, null),
				new CustomerModel("customerId3", "Acme s.r.o", "jana.dvorakova@acme.cz", "CZ1234567", null, null),
				new CustomerModel("customerId4", "Acme s.r.o", "jana.dvorakova@acme.cz", "CZ1234567", null, null),
				new CustomerModel("customerId5", "Acme s.r.o", "jana.dvorakova@acme.cz", "CZ1234567", null, null));

		Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(customers));
		StepVerifier.create(service.findAllCustomers()).expectNextCount(5).verifyComplete();
	}
}
