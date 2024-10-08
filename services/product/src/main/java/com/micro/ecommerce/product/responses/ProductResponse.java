package com.micro.ecommerce.product.responses;

import java.math.BigDecimal;

public record ProductResponse(
         Integer id,

         String name,

         String description,

         double availableQuantity,

         BigDecimal price,

         Integer categoryID,

         String categoryName,

         String categoryDescription

) {
}
