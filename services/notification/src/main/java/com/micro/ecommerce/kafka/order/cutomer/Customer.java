package com.micro.ecommerce.kafka.order.cutomer;

public record Customer(
        String id,

        String firstName,

        String lastName,

        String email
) {
}
