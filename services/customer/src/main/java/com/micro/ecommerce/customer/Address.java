package com.micro.ecommerce.customer;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Address {

    private String street;

    private String houseNumber;

    private String zipCode;
}
