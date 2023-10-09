/**
 * 
 */
package com.polarbookshop.customerservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

/**
 * @author Gbenga
 *
 */
@Value
public class AddressModel {
    
    String addressLine;
    String postalCode;
    String city;

    @JsonCreator
    public AddressModel(
            @JsonProperty("addressLine") String addressLine, 
            @JsonProperty("postalCode") String postalCode, 
            @JsonProperty("city") String city) {
        this.addressLine = addressLine;
        this.postalCode = postalCode;
        this.city = city;
    }  
}
