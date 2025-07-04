package com.academy.cinemaxx.configs;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Properties;

@Configuration
public class WebConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private MailConfig mailConfig;

    public WebConfig(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Bean
    public Properties properties(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", mailConfig.getSmtp().getHost());
        prop.put("mail.smtp.port", mailConfig.getSmtp().getPort());
        prop.put("mail.smtp.auth", mailConfig.getSmtp().isAuth());
        prop.put("mail.smtp.starttls.enable", mailConfig.getSmtp().getStarttls().isEnable());
        prop.put("mail.smtp.ssl.trust", mailConfig.getSmtp().getSsl().getTrust());
        return prop;
    }

    @Bean
    public PasswordAuthentication passwordAuthentication() {
        return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
    }

    @Bean
    public Session mailSession(@Qualifier("properties") Properties properties, PasswordAuthentication authentication){
        System.out.println(properties);
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        });
    }

    @Bean
    public Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        if (keyBytes.length < 64) {
            byte[] paddedKey = new byte[64];
            System.arraycopy(keyBytes, 0, paddedKey, 0, Math.min(keyBytes.length, 64));
            return Keys.hmacShaKeyFor(paddedKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
