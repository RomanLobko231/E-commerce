package com.micro.ecommerce.email;

import lombok.Getter;

public enum EmailType {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment Successfully processed"),

    ORDER_CONFIRMATION("order-confirmation.html", "Order Successfully processed");

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailType(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
