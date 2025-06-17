package com.academy.cinemaxx.security.providers;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.entities.UserOtp;
import com.academy.cinemaxx.repositories.UserOtpRepository;
import com.academy.cinemaxx.repositories.UserRepository;
import com.academy.cinemaxx.security.authentications.EmailAuthenticationToken;
import com.academy.cinemaxx.services.MailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final MailService mailService;

    public EmailAuthenticationProvider(
            UserRepository userRepository,
            UserOtpRepository userOtpRepository,
            MailService mailService) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
        this.mailService = mailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailAuthenticationToken authToken = (EmailAuthenticationToken) authentication;
        String email = authToken.getPrincipal().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        String otp = generateOTP();

        UserOtp userOtp = new UserOtp();
        userOtp.setUser(user);
        userOtp.setOtp(otp);
        userOtp.setUsed(false);
        userOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        userOtpRepository.save(userOtp);

        try {
            String message = String.format("""
                    <h1>Cinemaxx OTP Verification</h1>
                    <p>Your OTP code is: <strong>%s</strong></p>
                    <p>This code will expire in 5 minutes.</p>
                    """, otp);
            mailService.sendMail(message);
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to send OTP");
        }

        return new EmailAuthenticationToken(email, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }
} 