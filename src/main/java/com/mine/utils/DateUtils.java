package com.mine.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * @author 杜晓鹏
 * @create 2019-01-08 15:06
 */
public class DateUtils {
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * Date -> String
     * @param date 传入的时间
     */
    public static String dateToString(Date date, String format) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }
    public static String dateToString(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    /**
     *
     * String  -> Date
     *
     */
    public static Date stringToDated(String dateString) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormat.parseDateTime(dateString);
        return dateTime.toDate();
    }
    public static Date stringToDated(String dateString,String pattern) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(pattern);
        DateTime dateTime = dateTimeFormat.parseDateTime(dateString);
        return dateTime.toDate();
    }
}
