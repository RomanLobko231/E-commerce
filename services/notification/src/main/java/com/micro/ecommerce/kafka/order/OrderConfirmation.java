package com.micro.ecommerce.kafka.order;

import com.micro.ecommerce.kafka.order.cutomer.Customer;
import com.micro.ecommerce.kafka.order.product.Product;
import com.micro.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (

        String orderReference,

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Customer customer,

        List<Product> products

) {
}
