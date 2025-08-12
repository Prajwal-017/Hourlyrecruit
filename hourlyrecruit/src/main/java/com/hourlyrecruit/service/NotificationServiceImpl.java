package com.hourlyrecruit.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("📧 Email sent to " + to + " | Subject: " + subject + " | Body: " + body);
    }

    @Override
    public void sendSMS(String phoneNumber, String message) {
        System.out.println("📩 SMS sent to " + phoneNumber + " | Message: " + message);
    }

    @Override
    public void sendWhatsApp(String phoneNumber, String message) {
        System.out.println("💬 WhatsApp message sent to " + phoneNumber + " | Message: " + message);
    }
}
