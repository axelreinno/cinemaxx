package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.services.MailService;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final Session session;

    public MailServiceImpl(Session session) {
        super();
        this.session = session;
    }

    public void sendMail(String messages) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@mailinator.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("axel.fabiyanto@mailinator.com"));
        message.setSubject("Cinemaxx: Kode Verifikasi (OTP) untuk Verifikasi Identitas");
        String msg = messages;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
