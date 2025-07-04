package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.dtos.request.LoginRequestDTO;
import com.academy.cinemaxx.dtos.request.RegisterRequestDTO;
import com.academy.cinemaxx.dtos.request.VerifyRequestDTO;
import com.academy.cinemaxx.dtos.response.AuthResponseDTO;
import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.entities.UserOtp;
import com.academy.cinemaxx.enums.UserRole;
import com.academy.cinemaxx.exceptions.BadRequestRuntimeException;
import com.academy.cinemaxx.exceptions.LoginException;
import com.academy.cinemaxx.repositories.UserOtpRepository;
import com.academy.cinemaxx.repositories.UserRepository;
import com.academy.cinemaxx.security.authentications.RawAccessJwtToken;
import com.academy.cinemaxx.security.utils.JwtTokenFactory;
import com.academy.cinemaxx.services.AuthService;
import com.academy.cinemaxx.services.MailService;
import com.academy.cinemaxx.utils.HelperUtils;

import jakarta.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final JwtTokenFactory jwtTokenFactory;

    public AuthServiceImpl(
            UserRepository userRepository,
            UserOtpRepository userOtpRepository,
            BCryptPasswordEncoder passwordEncoder,
            MailService mailService,
            JwtTokenFactory jwtTokenFactory
    ) {
        this.userRepository = userRepository;
        this.userOtpRepository = userOtpRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Transactional
    public void login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(LoginException::new);

        String otp = HelperUtils.generateOTP();
        UserOtp userOtp = new UserOtp();
        userOtp.setUser(user);
        userOtp.setOtp(passwordEncoder.encode(otp));
        userOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        userOtp.setUsed(false);
        userOtpRepository.save(userOtp);

        mailService.sendOtpEmail(user.getEmail(), otp);
    }

    @Transactional
    public AuthResponseDTO verify(VerifyRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestRuntimeException("Invalid OTP"));

        UserOtp userOtp = userOtpRepository.findFirstByUserAndIsUsedFalseAndExpiresAtAfterOrderByCreatedAtDesc(user, LocalDateTime.now())
            .orElseThrow(() -> new BadRequestRuntimeException("Invalid OTP"));


        if (!passwordEncoder.matches(request.otp(), userOtp.getOtp())) {
            throw new BadRequestRuntimeException("Invalid OTP");
        }

        if (userOtp.isUsed()) {
            throw new BadRequestRuntimeException("Invalid OTP");
        }

        if (userOtp.isExpired()) {
            throw new BadRequestRuntimeException("OTP has expired");
        }


        userOtp.setUsed(true);
        userOtpRepository.save(userOtp);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        RawAccessJwtToken accessToken = jwtTokenFactory.createAccessJwtToken(user, authorities);

        return new AuthResponseDTO(
                user.getSecureId(),
                accessToken.getToken(),
                accessToken.getToken(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public void register(RegisterRequestDTO request) {
        Optional<User> user = userRepository.findByEmail(request.email());

        if(user.isPresent()) {
            throw new BadRequestRuntimeException("Email already registered");
        }

        User newUser = new User();
        newUser.setName(request.fullName());
        newUser.setEmail(request.email());
        newUser.setPhone(request.phoneNumber());
        newUser.setRole(UserRole.ROLE_USER);
        userRepository.save(newUser);
    }

}
