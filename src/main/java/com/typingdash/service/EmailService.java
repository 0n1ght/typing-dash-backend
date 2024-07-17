package com.typingdash.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService {
    void sendEmailWithAttachment(String to, String filePath) throws MessagingException, IOException;
    void sendEmail(String toEmail, String subject, String message);
    void sendToken(String toEmail, int token);
}