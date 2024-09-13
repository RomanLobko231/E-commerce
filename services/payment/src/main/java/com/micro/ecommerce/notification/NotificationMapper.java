package com.micro.ecommerce.notification;

import com.micro.ecommerce.payment.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {

    public PaymentNotification fromRequestToNotification(PaymentRequest request) {
        return new PaymentNotification(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstName(),
                request.customer().lastName(),
                request.customer().email()
        );
    }
}
