/**
 * 
 */
package com.polarbookshop.customerservice.validation;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
interface BaseValidator<T> {
    
    Mono<T> validate (T t);    
}
