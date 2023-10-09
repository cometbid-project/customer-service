/**
 * 
 */
package com.polarbookshop.customerservice.web;

import com.polarbookshop.customerservice.models.CustomerModel;
import com.polarbookshop.customerservice.services.CustomerService;
import com.polarbookshop.customerservice.validation.CustomerValidator;

import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
@Value
@Component
class CustomerHandler {

	  CustomerService customerService;

	  Mono<ServerResponse> postCustomer(ServerRequest request) {
	    CustomerValidator validator = new CustomerValidator();
	    //Mono<CustomerModel> body = request.bodyToMono(CustomerModel.class);
	    //Mono<CustomerModel> result = body.flatMap(b -> customerService.createCustomer(b));
	    //return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, CustomerModel.class);
	    return request
	      .bodyToMono(CustomerModel.class)
	      .flatMap(validator::validate)
	      .flatMap(customerService::createCustomer)
	      .flatMap(result ->
			        ServerResponse
			          .ok()
			          .contentType(MediaType.APPLICATION_JSON)
			          .bodyValue(result)
	      ).log()
	      .onErrorResume(err -> ServerResponse.badRequest().build());
	  }

	  Mono<ServerResponse> putCustomer(ServerRequest request) {
	    CustomerValidator validator = new CustomerValidator();
	    
	    return request
	      .bodyToMono(CustomerModel.class)
	      .flatMap(validator::validate)
	      .flatMap(customerService::updateCustomer)
	      .flatMap(result ->
			        ServerResponse
			          .ok()
			          .contentType(MediaType.APPLICATION_JSON)
			          .bodyValue(result)
	      )
	      .onErrorResume(err -> ServerResponse.badRequest().build());
	  }

	  Mono<ServerResponse> deleteCustomer(ServerRequest request) {
	    String id = request.pathVariable("id");
	    
	    Mono<Void> result = customerService.removeCustomer(id);
	    return ServerResponse
	      .ok()
	      .contentType(MediaType.APPLICATION_JSON)
	      .body(result, Void.class);
	  }

	  Mono<ServerResponse> getCustomerById(ServerRequest request) {
	    String id = request.pathVariable("id");
	    //Mono<CustomerModel> result = customerService.findCustomerById(id);
	    //return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(result, CustomerModel.class);
	    return customerService
	      .findCustomerById(id)
	      .flatMap(result ->
			        ServerResponse
			          .ok()
			          .contentType(MediaType.APPLICATION_JSON)
			          .bodyValue(result)
	      ).log()
	      .switchIfEmpty(ServerResponse.notFound().build());
	  }

	  Mono<ServerResponse> getAllCustomers(ServerRequest request) {
	    Flux<CustomerModel> result = customerService.findAllCustomers();
	    
	    return ServerResponse
	      .ok()
	      .contentType(MediaType.APPLICATION_JSON)
	      .body(result, CustomerModel.class);
	  }
	}

