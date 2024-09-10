package com.micro.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .customerId(request.customerId())
                .totalAmount(request.amount())
                .reference(request.reference())
                .paymentMethod(request.paymentMethod())
                .build();
    }
}
