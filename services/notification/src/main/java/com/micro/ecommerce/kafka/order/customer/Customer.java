package com.micro.ecommerce.kafka.order.customer;

public record Customer(
        String id,

        String firstName,

        String lastName,

        String email
) {
}
