package com.hourlyrecruit.service;

public interface NotificationService {
    void sendEmail(String to, String subject, String body);
    void sendSMS(String phoneNumber, String message);
    void sendWhatsApp(String phoneNumber, String message);
}
