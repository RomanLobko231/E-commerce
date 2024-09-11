package com.micro.ecommerce.customer;

public record CustomerInfoResponse(

        String id,

        String firstName,

        String lastName,

        String email
) {
}
