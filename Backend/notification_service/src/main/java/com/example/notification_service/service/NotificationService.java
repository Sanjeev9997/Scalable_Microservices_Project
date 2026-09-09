package com.example.notification_service.service;

import com.example.notification_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Logger log= LoggerFactory.getLogger(NotificationService.class);
//    private final JavaMailSender mailSender;
    public void sendEmail(NotificationRequest request) {
           log.info("Sending email notification");
//         SimpleMailMessage mailMessage = new SimpleMailMessage();
//         mailMessage.setTo(request.getEmail());
//         mailMessage.setSubject("Payment Successful");
//         mailMessage.setText(
//                 "Hello,\n\n" +
//                         "Your payment was successful.\n\n" +
//                         "Order Id : " + request.getOrderId() +
//                         "\nPayment Id : " + request.getPaymentId() +
//                         "\nAmount : ₹" + request.getAmount() +
//                         "\nStatus : " + request.getStatus()
//                 );
//         mailSender.send(mailMessage);
        log.info("Email Sent");
    }
}
