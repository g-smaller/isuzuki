package com.isuzuki.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class LocalTimeFormatUtils {
    public static final String EXTENDED_DATE_FORMAT = "yyyy-MM-dd";
    public static final String EXTENDED_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static ZonedDateTime getCurrentZonedDatetime() {
        return getZonedDatetime(new Date());
    }

    public static ZonedDateTime getZonedDatetime(Date date) {
        return getZonedDatetime(date, ZoneId.systemDefault());
    }

    public static ZonedDateTime getZonedDatetime(Date date, ZoneId zone) {
        return date.toInstant().atZone(zone);
    }

    public static Date parseDatetime(String date, String pattern) {
        try {
            return date == null || date.isEmpty() ? null : DateUtils.parseDate(date.trim(), pattern);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String format(Date date, String pattern) {
        return date == null ? "" : DateFormatUtils.format(date, pattern);
    }

    public static int diffDays(Date startTime) {
        return diffDays(startTime, new Date());
    }

    public static int diffDays(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return diffDays(startTime.getTime(), endTime.getTime(), false);
    }

    public static int diffDays(Date startTime, Date endTime, boolean inclusiveDay) {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return diffDays(startTime.getTime(), endTime.getTime(), inclusiveDay);
    }

    public static int diffDays(long startTime, long endTime, boolean inclusiveDay) {
        if (startTime <= 0 || endTime <= 0 || endTime < startTime) {
            return 0;
        }
        System.out.println(startTime + " " + endTime);
        long l = endTime - startTime;
        int days = (int) (l / 86400000);
        if (inclusiveDay) {
            if (l % 86400000 > 0) {
                days += 1;
            }
        }
        return days;
    }

    public static int getCurrentHour() {
        return getHour(new Date());
    }

    public static int getHour(Date date) {
        if (date == null) {
            return -1;
        }
        String hh = format(date, "HH");
        return Integer.valueOf(hh);
    }

    public static int getMinute(Date date) {
        if (date == null) {
            return -1;
        }
        String mm = format(date, "mm");
        return Integer.valueOf(mm);
    }

    public static int getSecond(Date date) {
        if (date == null) {
            return -1;
        }
        String ss = format(date, "ss");
        return Integer.valueOf(ss);
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
}
