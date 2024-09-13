package com.micro.ecommerce.payment;

import com.micro.ecommerce.customer.Customer;
import jakarta.validation.Valid;

import java.math.BigDecimal;

public record PaymentRequest(

        Integer id,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        @Valid
        Customer customer
) {
}
