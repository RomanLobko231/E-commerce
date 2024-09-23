package com.micro.ecommerce.kafka;

import com.micro.ecommerce.customer.CustomerInfoResponse;
import com.micro.ecommerce.order.PaymentMethod;
import com.micro.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        CustomerInfoResponse customer,

        List<PurchaseResponse> products
) {
}
