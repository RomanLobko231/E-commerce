package com.micro.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

public record Customer(

        @NotBlank(message = "Customer Id is required")
        String id,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @Email(message = "Email is not formatted correctly")
        @NotBlank(message = "Email is required")
        String email
) {
}
