package com.academy.cinemaxx.security.handlers;

import com.academy.cinemaxx.security.authentications.EmailAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmailAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private static final String OTP_EMAIL_SESSION_KEY = "OTP_EMAIL";

    public EmailAuthenticationSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        EmailAuthenticationToken emailAuthenticationToken = (EmailAuthenticationToken) authentication;
        String email = emailAuthenticationToken.getPrincipal().toString();

        HttpSession session = request.getSession();
        session.setAttribute(OTP_EMAIL_SESSION_KEY, email);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> body = new HashMap<>();
        body.put("status", "PENDING_OTP_VERIFICATION");
        body.put("message", "OTP has been sent to your email");

        objectMapper.writeValue(response.getWriter(), body);
    }
} 