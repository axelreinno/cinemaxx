package com.academy.cinemaxx.security.handlers;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.security.authentications.OtpAuthenticationToken;
import com.academy.cinemaxx.security.utils.JwtTokenFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OtpAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtTokenFactory tokenFactory;

    public OtpAuthenticationSuccessHandler(ObjectMapper objectMapper, JwtTokenFactory tokenFactory) {
        this.objectMapper = objectMapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OtpAuthenticationToken authToken = (OtpAuthenticationToken) authentication;
        User user = (User) authToken.getPrincipal();

        try {
            String token = tokenFactory.createAccessJwtToken(user, authToken.getAuthorities()).getToken();

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, String> resultmap = new HashMap<>();
            resultmap.put("status", "AUTHENTICATION_SUCCESSFUL");
            resultmap.put("message", "Authentication successful");
            resultmap.put("token", token);

            objectMapper.writeValue(response.getWriter(), resultmap);
        } catch (Exception e) {
            throw e;
        }
    }
}
