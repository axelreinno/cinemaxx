package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.services.MailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final Session session;

    public MailServiceImpl(Session session) {
        this.session = session;
    }

    @Override
    public void sendMail(String email, String message) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("noreply@cinemaxx.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject("Cinemaxx Notification");
            mimeMessage.setContent(message, "text/html; charset=utf-8");
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendOtpEmail(String email, String otp) {
        String message = String.format("""
                <h1>Cinemaxx OTP Verification</h1>
                <p>Your OTP code is: <strong>%s</strong></p>
                <p>This code will expire in 5 minutes.</p>
                """, otp);
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("noreply@cinemaxx.com"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            mimeMessage.setSubject("Cinemaxx OTP Verification");
            mimeMessage.setContent(message, "text/html; charset=utf-8");
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
}
