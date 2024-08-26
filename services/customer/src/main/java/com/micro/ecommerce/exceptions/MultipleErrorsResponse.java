package com.micro.ecommerce.exceptions;

import java.util.Map;

public record MultipleErrorsResponse(
        Map<String, String> errors
) {
}
