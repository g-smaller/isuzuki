package com.isuzuki.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;

public class LocalTimeFormatUtils {
    public static final String EXTENDED_DATE_FORMAT = "yyyy-MM-dd";
    public static final String EXTENDED_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static int today() {
        return today(new Date());
    }

    public static int today(Date date) {
        String today = format(date, "yyyyMMdd");
        return Integer.parseInt(today);
    }

    public static int yesterday() {
        return yesterday(new Date());
    }

    public static int yesterday(Date now) {
        Date date = DateUtils.addDays(now, -1);
        return today(date);
    }

    public static ZonedDateTime getCurrentZonedDatetime() {
        return getZonedDatetime(new Date());
    }

    public static ZonedDateTime getZonedDatetime(Date date) {
        return getZonedDatetime(date, ZoneId.systemDefault());
    }

    public static ZonedDateTime getZonedDatetime(Date date, ZoneId zone) {
        return date.toInstant().atZone(zone);
    }

    public static String getDateTimeOfWeekMonday() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfWeekMonday(now);
    }

    public static String getDateTimeOfWeekSunday() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfWeekSunday(now);
    }

    public static String getDateTimeOfWeekMonday(LocalDate now) {
        LocalDate monday = now.with(DayOfWeek.MONDAY);
        return monday.toString() + " 00:00:00";
    }

    public static String getDateTimeOfWeekSunday(LocalDate now) {
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);
        return sunday.toString() + " 23:59:59";
    }

    public static String getDateTimeOfMonthStart() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfMonthStart(now);
    }

    public static String getDateTimeOfMonthEnd() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfMonthEnd(now);
    }

    public static String getDateTimeOfMonthStart(LocalDate now) {
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        return firstDayOfMonth.toString() + " 00:00:00";
    }

    public static String getDateTimeOfMonthEnd(LocalDate now) {
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        return lastDayOfMonth.toString() + " 23:59:59";
    }

    public static String getDateTimeOfYearStart() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfYearStart(now);
    }

    public static String getDateTimeOfYearEnd() {
        LocalDate now = LocalDate.now();
        return getDateTimeOfYearEnd(now);
    }

    public static String getDateTimeOfYearStart(LocalDate now) {
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfYear());
        return firstDayOfMonth.toString() + " 00:00:00";
    }

    public static String getDateTimeOfYearEnd(LocalDate now) {
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfYear());
        return lastDayOfMonth.toString() + " 23:59:59";
    }

    public static String getStartTimeOfDay() {
        return getStartTimeOfDay(new Date());
    }

    public static String getEndTimeOfDay() {
        return getEndTimeOfDay(new Date());
    }

    public static String getStartTimeOfDay(Date date) {
        return formatDatetime(getStartDateOfDay(date));
    }

    public static String getEndTimeOfDay(Date date) {
        return formatDatetime(getEndDateOfDay(date));
    }

    public static Date getStartDateOfDay() {
        return getStartDateOfDay(new Date());
    }

    public static Date getEndDateOfDay() {
        return getEndDateOfDay(new Date());
    }

    public static Date getStartDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return setHours(setMinutes(setSeconds(setMilliseconds(date, 0), 0), 0), 0);
    }

    public static Date getEndDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        return setHours(setMinutes(setSeconds(setMilliseconds(date, 0), 59), 59), 23);
    }

    public static String formatDate() {
        return format(new Date());
    }

    public static String format(Date date) {
        return format(date, EXTENDED_DATE_FORMAT);
    }

    public static String format(Date date, String pattern) {
        return date == null ? "" : DateFormatUtils.format(date, pattern);
    }

    public static String formatDatetime() {
        return format(new Date(), EXTENDED_DATETIME_FORMAT);
    }

    public static String formatDatetime(Date date) {
        return format(date, EXTENDED_DATETIME_FORMAT);
    }

    public static Optional<Integer> diffDays(Date startTime) {
        return diffDays(startTime, new Date());
    }

    public static Optional<Integer> diffDays(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return Optional.empty();
        }
        return diffDays(startTime.getTime(), endTime.getTime(), false);
    }

    public static Optional<Integer> diffDays(Date startTime, Date endTime, boolean inclusiveDay) {
        if (startTime == null || endTime == null) {
            return Optional.empty();
        }
        return diffDays(startTime.getTime(), endTime.getTime(), inclusiveDay);
    }

    public static Optional<Integer> diffDays(long startTime, long endTime, boolean inclusiveDay) {
        if (startTime <= 0 || endTime <= 0) {
            return Optional.empty();
        }
        // System.out.println(startTime + " " + endTime);
        long l = endTime - startTime;
        int days = (int) (l / 86400000);
        if (inclusiveDay) {
            if (l % 86400000 != 0) {
                if (l < 0) {
                    days += -1;
                }else {
                    days += 1;
                }
            }
        }
        return Optional.of(days);
    }

    public static Date millisToDate(Long millisSeconds) {
        return millisSeconds == null || millisSeconds <= 0 ? null : new Date(millisSeconds);
    }

    public static Date parseDatetime(String date) {
        return parseDatetime(date, EXTENDED_DATETIME_FORMAT);
    }

    public static Date parseDate(String date) {
        return parseDatetime(date, EXTENDED_DATE_FORMAT);
    }

    public static Date parseDatetime(String date, String pattern) {
        try {
            return date == null || date.isEmpty() ? null : org.apache.commons.lang3.time.DateUtils.parseDate(date.trim(), pattern);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCurrentHour() {
        return getHour(new Date());
    }

    public static int getHour(Date date) {
        if (date == null) {
            return -1;
        }
        String hh = format(date, "HH");
        return Integer.parseInt(hh);
    }

    public static int getMinute(Date date) {
        if (date == null) {
            return -1;
        }
        String mm = format(date, "mm");
        return Integer.parseInt(mm);
    }

    public static int getSecond(Date date) {
        if (date == null) {
            return -1;
        }
        String ss = format(date, "ss");
        return Integer.parseInt(ss);
    }

    public static int getDaysOfMonth(String date, String pattern) {
        return getDaysOfMonth(parseDatetime(date, pattern));
    }

    public static int getDaysOfMonth(Date date) {
        return getZonedDatetime(date).toLocalDate().lengthOfMonth();
    }

    public static int getYear() {
        return getYear(new Date());
    }

    public static int getYear(Date date) {
        return getZonedDatetime(date).toLocalDate().getYear();
    }

    public static int getMonth() {
        return getYear(new Date());
    }

    public static int getMonth(Date date) {
        return getZonedDatetime(date).toLocalDate().getMonthValue();
    }

    public static int howOld(String birthday) {
        if (StringUtils.isBlank(birthday)) {
            return 0;
        }
        Date date = null;
        try {
            date = parseDatetime(birthday, EXTENDED_DATE_FORMAT);
        }catch (Exception e) {

        }
        if (date == null) {
            try {
                date = parseDatetime(birthday, EXTENDED_DATETIME_FORMAT);
            }catch (Exception e) {

            }
        }
        if (date == null) {
            return 0;
        }
        ZonedDateTime birthdayZoneDatetime = getZonedDatetime(date);
        ZonedDateTime currentZonedDatetime = getCurrentZonedDatetime();
        int age = currentZonedDatetime.getYear() - birthdayZoneDatetime.getYear();
        if (currentZonedDatetime.getMonthValue() < birthdayZoneDatetime.getMonthValue()) {
            age = age - 1;
        }
        return age;
    }

    public static Date addYears(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addYears(date, amount);
    }

    public static Date addMonths(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMonths(date, amount);
    }
    public static Date addDays(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
    }

    public static Date addHours(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addHours(date, amount);
    }

    public static Date addMinutes(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMinutes(date, amount);
    }

    public static Date addSeconds(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addSeconds(date, amount);
    }

    public static Date addMilliseconds(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMilliseconds(date, amount);
    }

    public static Date setYears(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setYears(date, amount);
    }

    public static Date setMonths(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setMonths(date, amount);
    }
    public static Date setDays(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setDays(date, amount);
    }

    public static Date setHours(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setHours(date, amount);
    }

    public static Date setMinutes(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setMinutes(date, amount);
    }

    public static Date setSeconds(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setSeconds(date, amount);
    }

    public static Date setMilliseconds(final Date date, final int amount) {
        return org.apache.commons.lang3.time.DateUtils.setMilliseconds(date, amount);
    }

    public static boolean eq(ZonedDateTime o1, ZonedDateTime o2, TimeUnit... timeUnits) {
        for (TimeUnit timeUnit : timeUnits) {
            if (timeUnit.eq(o1, o2)) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static boolean eq(Date o1, Date o2, TimeUnit... timeUnits) {
        ZonedDateTime zonedDatetime1 = getZonedDatetime(o1);
        ZonedDateTime zonedDatetime2 = getZonedDatetime(o2);
        return eq(zonedDatetime1, zonedDatetime2, timeUnits);
    }

    public static boolean eq(Date o1, TimeUnit... timeUnits) {
        return eq(o1, new Date(), timeUnits);
    }

    public static boolean eqYmd(Date o1) {
        return eqYmd(o1, new Date());
    }

    public static boolean eqYmd(Date o1, Date o2) {
        return eq(o1, o2, TimeUnit.YEAR, TimeUnit.MONTH, TimeUnit.DAYS);
    }

    public enum TimeUnit {
        YEAR() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getYear() == o2.getYear();
            }
        },
        MONTH() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getMonthValue() == o2.getMonthValue();
            }
        },
        DAYS() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getDayOfMonth() == o2.getDayOfMonth();
            }
        },
        HOUR() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getHour() == o2.getHour();
            }
        },
        MINUTE() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getMinute() == o2.getMinute();
            }
        },
        SECOND() {
            @Override
            public boolean eq(ZonedDateTime o1, ZonedDateTime o2) {
                return o1.getSecond() == o2.getSecond();
            }
        },
        ;

        public abstract boolean eq(ZonedDateTime o1, ZonedDateTime o2);
    }
}
