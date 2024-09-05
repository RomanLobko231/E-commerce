package com.micro.ecommerce.product.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        Integer id,

        @NotNull(message = "Name is required")
        String name,

        @NotNull(message = "Description is required")
        String description,

        @Positive(message = "Quantity must be of positive value")
        @NotNull(message = "Quantity is required")
        double availableQuantity,

        @Positive(message = "Price must be of positive value")
        @NotNull(message = "Price is required")
        BigDecimal price,

        @NotNull(message = "Category is required")
        Integer categoryId

) {
}
