package com.academy.cinemaxx.security.utils;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.security.authentications.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {

    private final Key key;

    public JwtTokenFactory(Key key) {
        this.key = key;
    }

    public RawAccessJwtToken createAccessJwtToken(User user, Collection<? extends GrantedAuthority> collection) {
        if (user.getEmail() == null) {
            throw new IllegalArgumentException("Cannot create JWT Token without email");
        }

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("scopes", collection.stream().map(s -> s.getAuthority()).collect(Collectors.toList()));
        claims.put("email", user.getEmail());
        claims.put("id", user.getSecureId());

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusDays(1);

        Date currentDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(currentDate)
                    .setExpiration(expirationDate)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact();
            return new RawAccessJwtToken(token);
        } catch (Exception e) {
            throw e;
        }
    }
}