package com.academy.cinemaxx.security.filters;

import com.academy.cinemaxx.dtos.request.OtpVerificationRequestDTO;
import com.academy.cinemaxx.security.authentications.OtpAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.stream.Collectors;

public class OtpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    public static final String OTP_EMAIL_SESSION_KEY = "OTP_EMAIL";
    public static final String OTP_AUTH_PROCESSED_KEY = "OTP_AUTH_PROCESSED";

    public OtpAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            ObjectMapper objectMapper) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "POST"));
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        if (request.getAttribute(OTP_AUTH_PROCESSED_KEY) != null) {
            return null;
        }
        request.setAttribute(OTP_AUTH_PROCESSED_KEY, true);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(OTP_EMAIL_SESSION_KEY) == null) {
            throw new BadCredentialsException("No OTP session found. Please request OTP first.");
        }

        String email = (String) session.getAttribute(OTP_EMAIL_SESSION_KEY);

        try {
            String body = request.getReader().lines().collect(Collectors.joining());
            if (body == null || body.trim().isEmpty()) {
                throw new BadCredentialsException("Request body is empty");
            }

            OtpVerificationRequestDTO verificationRequest = objectMapper.readValue(body, OtpVerificationRequestDTO.class);

            if (verificationRequest.otp() == null || verificationRequest.otp().isEmpty()) {
                throw new BadCredentialsException("OTP is required");
            }

            return this.getAuthenticationManager().authenticate(
                    new OtpAuthenticationToken(email, verificationRequest.otp())
            );

        } catch (IOException e) {
            throw new BadCredentialsException("Invalid request format");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(OTP_EMAIL_SESSION_KEY);
        }
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}