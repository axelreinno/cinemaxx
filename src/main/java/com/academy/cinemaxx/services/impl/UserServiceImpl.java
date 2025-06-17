package com.academy.cinemaxx.services.impl;

import com.academy.cinemaxx.entities.User;
import com.academy.cinemaxx.exceptions.BadRequestRuntimeException;
import com.academy.cinemaxx.repositories.UserRepository;
import com.academy.cinemaxx.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestRuntimeException("User not found"));
    }
}
