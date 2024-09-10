package com.micro.ecommerce.order;

import com.micro.ecommerce.product.PurchaseRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,

        String reference,

        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotNull(message = "Payment method must be valid")
        PaymentMethod paymentMethod,

        @NotBlank(message = "Customer Id is required")
        String customerId,

        @NotEmpty(message = "List must include at least one product")
        List<@Valid PurchaseRequest> purchaseRequests
) {

}
