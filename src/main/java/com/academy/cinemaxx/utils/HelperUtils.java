package com.academy.cinemaxx.utils;

import java.util.Random;

public class HelperUtils {
    public static String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    public static String normalize(String value) {
        return (value == null || value.isBlank()) ? "" : value;
    }
}
