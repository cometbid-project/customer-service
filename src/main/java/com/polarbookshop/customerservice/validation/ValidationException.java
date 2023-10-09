/**
 * 
 */
package com.polarbookshop.customerservice.validation;

import am.ik.yavi.core.ConstraintViolation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gbenga
 *
 */
@AllArgsConstructor
public class ValidationException extends RuntimeException {
    
    @Getter
    final List<ConstraintViolation> errors;       
}
