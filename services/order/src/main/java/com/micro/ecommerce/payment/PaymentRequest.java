package com.micro.ecommerce.payment;

import com.micro.ecommerce.customer.CustomerInfoResponse;
import com.micro.ecommerce.order.PaymentMethod;
import jakarta.validation.Valid;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        CustomerInfoResponse customer
) {
}
