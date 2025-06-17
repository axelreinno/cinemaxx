package com.academy.cinemaxx.security.exceptions;

import com.academy.cinemaxx.security.authentications.RawAccessJwtToken;
import org.springframework.security.core.AuthenticationException;


public class JwtExpiredTokenException extends AuthenticationException {
    private RawAccessJwtToken token;

    public JwtExpiredTokenException(RawAccessJwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token.getToken();
    }
}