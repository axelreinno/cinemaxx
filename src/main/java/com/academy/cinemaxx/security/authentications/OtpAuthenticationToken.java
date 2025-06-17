package com.academy.cinemaxx.security.authentications;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.principal = email;
        this.credentials = otp;
        setAuthenticated(false);
    }

    public OtpAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}