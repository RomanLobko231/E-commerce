package com.micro.ecommerce.kafka;

import com.micro.ecommerce.email.EmailService;
import com.micro.ecommerce.kafka.order.OrderConfirmation;
import com.micro.ecommerce.kafka.payment.PaymentConfirmation;
import com.micro.ecommerce.notification.Notification;
import com.micro.ecommerce.notification.NotificationRepository;
import com.micro.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;

    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic", groupId = "paymentGroup")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming a message from payment-topic <{}>", paymentConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        emailService.sendPaymentSuccessEmail(paymentConfirmation);

    }

    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming a message from order-topic <{}>", orderConfirmation);
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        emailService.sendOrderConfirmationEmail(orderConfirmation);
    }
}
