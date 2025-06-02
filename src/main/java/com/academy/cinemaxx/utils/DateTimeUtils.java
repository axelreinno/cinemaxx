package com.academy.cinemaxx.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Jakarta");

    public static long toEpochSecond(LocalDate localDate) {
        return toEpochSecond(localDate, DEFAULT_ZONE_ID);
    }

    public static long toEpochSecond(LocalDate localDate, ZoneId zoneId) {
        return localDate.atStartOfDay(zoneId).toEpochSecond();
    }

    public static long toEpochSecond(LocalDateTime localDateTime) {
        return toEpochSecond(localDateTime, DEFAULT_ZONE_ID);
    }

    public static long toEpochSecond(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toEpochSecond();
    }

    public static long toEpochMilli(LocalDate localDate) {
        return toEpochMilli(localDate, DEFAULT_ZONE_ID);
    }

    public static long toEpochMilli(LocalDate localDate, ZoneId zoneId) {
        return localDate.atStartOfDay(zoneId).toInstant().toEpochMilli();
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return toEpochMilli(localDateTime, DEFAULT_ZONE_ID);
    }

    public static long toEpochMilli(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    public static long toEpochDay(LocalDate localDate) {
        return localDate.toEpochDay();
    }

    public static LocalDate fromEpochDay(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }
}