package com.learning.java.todo.util;

import com.learning.java.todo.exception.DateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatterValidator {

    public static Date validateAndFormatDate(String date) throws DateException {
        Date targetDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.ENGLISH);
            targetDate = formatter.parse(date);
            if (targetDate.before(new Date())) {
                throw new DateException("3002", "Target Date can not be a past date.");
            }
        } catch (ParseException | DateException ex) {
            throw new DateException("3001", "Invalid Target Date Provided.");
        }
        return targetDate;
    }
}

