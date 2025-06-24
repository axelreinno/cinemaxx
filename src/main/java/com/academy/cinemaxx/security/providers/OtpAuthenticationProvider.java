package com.academy.cinemaxx.security.providers;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.entities.UserOtp;
import com.academy.cinemaxx.repositories.UserOtpRepository;
import com.academy.cinemaxx.repositories.UserRepository;
import com.academy.cinemaxx.security.authentications.OtpAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public OtpAuthenticationProvider(
            UserRepository userRepository,
            UserOtpRepository userOtpRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OtpAuthenticationToken authToken = (OtpAuthenticationToken) authentication;
        String email = authToken.getPrincipal().toString();
        String plainOtp = authToken.getCredentials().toString();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Invalid email"));

        UserOtp userOtp = userOtpRepository.findFirstByUserAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(user, LocalDateTime.now())
                .orElseThrow(() -> new BadCredentialsException("No OTP found"));

        if (!passwordEncoder.matches(plainOtp, userOtp.getOtp())) {
            throw new BadCredentialsException("Invalid OTP");
        }

        if (userOtp.isExpired()) {
            throw new BadCredentialsException("OTP has expired");
        }

        if (!userOtp.isUsed()) {
            userOtp.setUsed(true);
            userOtpRepository.save(userOtp);
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        return new OtpAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
