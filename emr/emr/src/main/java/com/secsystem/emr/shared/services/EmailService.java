package com.secsystem.emr.shared.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendHtmlEmail(String to, String subject, String message, String emailTemplate) throws MessagingException;
}

