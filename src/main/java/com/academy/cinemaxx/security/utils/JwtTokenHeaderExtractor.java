package com.academy.cinemaxx.security.utils;

import com.academy.cinemaxx.security.authentications.RawAccessJwtToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenHeaderExtractor {
    public static String HEADER_PREFIX = "Bearer ";

    public RawAccessJwtToken extract(String header) {
        if (!StringUtils.hasText(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        if (!header.startsWith(HEADER_PREFIX)) {
            throw new AuthenticationServiceException("Invalid authorization header format.");
        }

        return new RawAccessJwtToken(header.substring(HEADER_PREFIX.length()));
    }
}