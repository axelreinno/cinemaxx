package com.academy.cinemaxx.security.providers;

import com.academy.cinemaxx.audits.Principal;
import com.academy.cinemaxx.security.authentications.JwtAuthenticationToken;
import com.academy.cinemaxx.security.exceptions.JwtExpiredTokenException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final Key key;

    public JwtAuthenticationProvider(Key key) {
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) authentication;
        String token = authToken.getToken().getToken();

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new JwtExpiredTokenException(authToken.getToken(), "Token expired");
            }

            List<String> scopes = claims.get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            Principal principal = new Principal(claims.getSubject(), authorities);

            return new JwtAuthenticationToken(principal, authToken.getToken(), principal.getAuthorities());

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}