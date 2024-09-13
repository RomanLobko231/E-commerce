package com.micro.ecommerce.payment;

import com.micro.ecommerce.notification.NotificationMapper;
import com.micro.ecommerce.notification.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final NotificationProducer notificationProducer;

    private final NotificationMapper notificationMapper;

    public Integer createPayment(PaymentRequest request) {
        Payment newPayment = paymentRepository.save(
                paymentMapper.toPayment(request)
        );

        notificationProducer.sendPaymentNotification(
                notificationMapper.fromRequestToNotification(request)
        );

        return newPayment.getId();
    }
}
