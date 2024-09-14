package com.micro.ecommerce.kafka.order.product;

import java.math.BigDecimal;

public record Product(

        Integer id,

        String name,

        String description,

        BigDecimal price,

        double quantity
) {
}
