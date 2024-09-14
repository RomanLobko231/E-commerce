package com.micro.ecommerce.email;

import com.micro.ecommerce.kafka.order.OrderConfirmation;
import com.micro.ecommerce.kafka.order.product.Product;
import com.micro.ecommerce.kafka.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.sender-email}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(PaymentConfirmation paymentDetails) throws MessagingException {
        String customerName =  paymentDetails.customerFirstName() + " " + paymentDetails.customerLastName();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", paymentDetails.amount());
        variables.put("orderReference", paymentDetails.orderReference());

        sendHtmlTemplateEmail(paymentDetails.customerEmail(), EmailType.PAYMENT_CONFIRMATION, variables);
    }

    @Async
    public void sendOrderConfirmationEmail(OrderConfirmation orderDetails) throws MessagingException {
        String customerName = orderDetails.customer().firstName() + " " + orderDetails.customer().lastName();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", orderDetails.amount());
        variables.put("orderReference", orderDetails.orderReference());
        variables.put("products", orderDetails.products());

        sendHtmlTemplateEmail(orderDetails.customer().email(), EmailType.ORDER_CONFIRMATION, variables);
    }


    private void sendHtmlTemplateEmail(
            String customerEmail,
            EmailType type,
            Map<String, Object> templateVariables
    ) throws MessagingException {
        String htmlTemplate = configureTemplate(templateVariables, type);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        configureMimeMessage(mimeMessage, customerEmail, type, htmlTemplate);
        mailSender.send(mimeMessage);

        log.info("Email is sent to {}", customerEmail);
    }

    private String configureTemplate(Map<String, Object> templateVariables, EmailType emailType) {
        Context context = new Context();
        context.setVariables(templateVariables);
        return templateEngine.process(emailType.getTemplate(), context);
    }

    private void configureMimeMessage(MimeMessage mimeMessage, String customerEmail, EmailType emailType, String body)
            throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );
        helper.setFrom(senderEmail);
        helper.setTo(customerEmail);
        helper.setSubject(emailType.getSubject());
        helper.setText(body, true);
    }

}
