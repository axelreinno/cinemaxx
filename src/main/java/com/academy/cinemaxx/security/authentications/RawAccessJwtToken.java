package com.academy.cinemaxx.security.authentications;

public class RawAccessJwtToken {

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}