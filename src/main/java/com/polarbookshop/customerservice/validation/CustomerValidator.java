/**
 * 
 */
package com.polarbookshop.customerservice.validation;

import com.polarbookshop.customerservice.models.CustomerModel;
import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
@Log4j2 
public class CustomerValidator implements BaseValidator<CustomerModel> {

	private final Validator<CustomerModel> validator;

	public CustomerValidator() {
		validator = ValidatorBuilder.of(CustomerModel.class)
				.constraint(CustomerModel::getCompanyEmail, "companyEmail", c -> c.notNull().email())
				.constraint(CustomerModel::getCompanyName, "companyName", c -> c.notNull())
				.constraint(CustomerModel::getTaxId, "taxId", c -> c.pattern("^[a-zA-Z0-9_]*$")).build();
	}

	@Override
	public Mono<CustomerModel> validate(CustomerModel model) {
		ConstraintViolations violations = validator.validate(model);

		if (violations.isValid()) {
			return Mono.just(model);
		} else {
			log.info(violations.violations()); 
			return Mono.error(new ValidationException(violations.violations()));
		}
	}

}
