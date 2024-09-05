package com.micro.ecommerce.product.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(

        @NotNull(message = "Id must not be null")
        Integer productId,

        @NotNull(message = "Quantity must not be null")
        @Positive(message = "Quantity must be of positive value")
        double requestedQuantity
) {
}
