package com.academy.cinemaxx.services;

public interface MailService {
    void sendMail(String email, String message);
    void sendOtpEmail(String email, String otp);
}