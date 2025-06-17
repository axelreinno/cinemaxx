package com.academy.cinemaxx.security.authentications;

import com.academy.cinemaxx.audits.Principal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final RawAccessJwtToken rawAccessToken;
    private Principal principal;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Principal principal, RawAccessJwtToken token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.rawAccessToken = token;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public RawAccessJwtToken getToken() {
        return rawAccessToken;
    }
}