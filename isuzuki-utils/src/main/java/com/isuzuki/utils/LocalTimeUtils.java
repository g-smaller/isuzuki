package com.isuzuki.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.util.Date;

public final class LocalTimeUtils {

    public static final ZoneOffset UTC_8 = ZoneOffset.ofHours(8);
    public static final ZoneOffset CTT = UTC_8;
    private static final LocalTime FLOOR = LocalTime.of(0, 0, 0, 0);
    private static final LocalTime  CEILLING = LocalTime.of(23, 59, 59, 999_999_999);
    public static final int WEEK_DAY = 7;

    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date.getTime());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return toLocalDateTime(date.getTime());
    }

    public static LocalTime toLocalTime(Date date) {
        return toLocalTime(date.getTime());
    }

    public static LocalDate toLocalDate(long millis) {
        return toZoneDateTime(millis).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(long millis) {
        return toZoneDateTime(millis).toLocalDateTime();
    }

    public static LocalTime toLocalTime(long millis) {
        return toZoneDateTime(millis).toLocalTime();
    }

    public static ZonedDateTime toZoneDateTime(Date date) {
        return toZoneDateTime(date.getTime());
    }

    public static ZonedDateTime toZoneDateTime(Date date, ZoneId zoneId) {
        return toZoneDateTime(date.getTime(), zoneId);
    }

    public static ZonedDateTime toZoneDateTime(long millis) {
        return Instant.ofEpochMilli(millis).atOffset(UTC_8).toZonedDateTime();
    }

    public static ZonedDateTime toZoneDateTime(long millis, ZoneId zoneId) {
        return Instant.ofEpochMilli(millis).atZone(zoneId);
    }

    public static LocalDateTime floor() {
        return floor(LocalDateTime.now());
    }

    public static LocalDateTime floor(Date date) {
        return floor(toLocalDateTime(date));
    }

    public static LocalDateTime floor(int day) {
        return floor(LocalDateTime.now().minusDays(day));
    }

    public static LocalDateTime floor(LocalDateTime localDateTime) {
        return localDateTime.with(FLOOR);
    }

    public static LocalDateTime ceiling() {
        return ceiling(LocalDateTime.now());
    }

    public static LocalDateTime ceiling(Date date) {
        return ceiling(toLocalDateTime(date));
    }

    public static LocalDateTime ceiling(int day) {
        return ceiling(LocalDateTime.now().minusDays(day));
    }

    public static LocalDateTime ceiling(LocalDateTime localDateTime) {
        return localDateTime.with(CEILLING);
    }

    /**
     * 获取当前时间 - 月起始时间
     * @return
     */
    public static Monthly getMonthly() {
        return getMonthly(LocalDateTime.now());
    }

    /**
     * 获取指定时间 - 月起始时间
     * @param date
     * @return
     */
    public static Monthly getMonthly(Date date) {
        return getMonthly(toLocalDateTime(date));
    }

    /**
     * 获取指定时间 - 月起始时间
     * @param localDate
     * @return
     */
    public static Monthly getMonthly(LocalDate localDate) {
        return getMonthly(localDate.atStartOfDay());
    }

    /**
     * 获取指定时间 - 月起始时间
     * @param localDateTime
     * @return
     */
    public static Monthly getMonthly(LocalDateTime localDateTime) {
        int monthMaxDay = getMonthMaxDay(localDateTime);
        int current = localDateTime.getDayOfMonth();
        int first = current - 1;
        int last = monthMaxDay - current;
        return new Monthly(localDateTime, floor(localDateTime.minusDays(first)), ceiling(localDateTime.plusDays(last)));
    }

    /**
     * 获取当前 - 月天数
     * @return
     */
    public static int getMonthMaxDay() {
        return getMonthMaxDay(LocalDate.now());
    }

    /**
     * 获取指定时间 - 月天数
     * @param date
     * @return
     */
    public static int getMonthMaxDay(Date date) {
        return getMonthMaxDay(toLocalDate(date));
    }

    /**
     * 获取指定时间 - 月天数
     * @param localDateTime
     * @return
     */
    public static int getMonthMaxDay(LocalDateTime localDateTime) {
        return getMonthMaxDay(localDateTime.toLocalDate());
    }

    /**
     * 获取指定时间 - 月天数
     * @param localDate
     * @return
     */
    public static int getMonthMaxDay(LocalDate localDate) {
        return localDate.getMonth().maxLength();
    }

    /**
     * 获取当前时间 - 周起始时间
     * @return
     */
    public static Weekly getWeekly() {
        return getWeekly(LocalDateTime.now());
    }

    /**
     * 获取指定时间 - 周起始时间
     * @param date
     * @return
     */
    public static Weekly getWeekly(Date date) {
        return getWeekly(toLocalDateTime(date));
    }

    /**
     * 获取指定时间 - 周起始时间
     * @param localDate
     * @return
     */
    public static Weekly getWeekly(LocalDate localDate) {
        return getWeekly(localDate.atStartOfDay());
    }

    /**
     * 获取指定时间 - 周起始时间
     * @param localDateTime
     * @return
     */
    public static Weekly getWeekly(LocalDateTime localDateTime) {
        int current = localDateTime.getDayOfWeek().getValue();
        int first = current - 1;
        int last = WEEK_DAY - current;
        return new Weekly(localDateTime, floor(localDateTime.minusDays(first)), ceiling(localDateTime.plusDays(last)));
    }


    public static Date toDate(LocalDate localDate) {
        // return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Date.from(localDate.atStartOfDay().toInstant(UTC_8));
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(UTC_8));
    }

    public static class Weekly extends RangeTime {
        public Weekly(LocalDateTime current, LocalDateTime first, LocalDateTime last) {
            super(current, first, last);
        }
    }

    public static class Monthly extends RangeTime {


        public Monthly(LocalDateTime current, LocalDateTime first, LocalDateTime last) {
            super(current, first, last);
        }
    }

    static class RangeTime {
        private LocalDateTime first;
        private LocalDateTime last;
        private LocalDateTime current;

        public RangeTime(LocalDateTime current, LocalDateTime first, LocalDateTime last) {
            this.current = current;
            this.first = first;
            this.last = last;
        }

        public LocalDateTime getFirst() {
            return first;
        }

        public LocalDateTime getLast() {
            return last;
        }

        public LocalDateTime getCurrent() {
            return current;
        }

        public LocalDate getFirstLocalDate() {
            return getFirst().toLocalDate();
        }
        public LocalDate getLastLocalDate() {
            return getLast().toLocalDate();
        }

        public LocalDate getCurrentLocalDate() {
            return getCurrent().toLocalDate();
        }

        public Date getFirstDate() {
            return toDate(getFirst());
        }

        public Date getLastDate() {
            return toDate(getLast());
        }

        public Date getCurrentDate() {
            return toDate(getCurrent());
        }
    }

    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().getDayOfWeek().getValue());
        System.out.println(LocalTimeUtils.toDate(LocalDate.now()));
        System.out.println(LocalTimeUtils.toDate(LocalDateTime.now()));
        System.out.println(ZoneId.systemDefault());

        System.out.println(LocalTimeUtils.getMonthMaxDay());

        Monthly weekly = LocalTimeUtils.getMonthly();
        System.out.println(weekly.getFirst());
        System.out.println(weekly.getLast());
        System.out.println(weekly.getFirstLocalDate());
        System.out.println(weekly.getLastLocalDate());
        System.out.println(weekly.getFirstDate());
        System.out.println(weekly.getLastDate());
    }

}


