package com.micro.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotNull(message = "First name is required")
        @NotBlank(message = "First name should not be blank")
        String firstName,

        @NotNull(message = "Last name is required")
        @NotBlank(message = "Last name should not be blank")
        String lastName,

        @NotNull(message = "Email is required")
        @NotBlank(message = "Email should not be blank")
        @Email(message = "Email is not valid")
        String email,

        @NotNull(message = "Address is required")
        Address address
) {
}
