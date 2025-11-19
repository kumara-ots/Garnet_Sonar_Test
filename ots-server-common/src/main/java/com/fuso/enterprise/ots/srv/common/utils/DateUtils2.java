
package com.fuso.enterprise.ots.srv.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.common.exception.ErrorEnumeration;

public class DateUtils2 {

    public static LocalDate asLocalDate(Date date) {
        return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public static String asLocalDate(LocalDate localDate) {
        return localDate != null ? parseDate(asDate(localDate)) : null;
    }

    public static LocalDate asLocalDate(String date) {
        return date != null && !date.trim().isEmpty() ? asLocalDate(parseDate(date)) : null;
    }

    public static Date asDate(LocalDate localDate) {
        return localDate != null ? Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()) : null;
    }

    public static Date asDate(String date) {
        return date != null && date.trim().isEmpty() ? parseDate(date) : null;
    }

    private static Date parseDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException parseException) {
            throw new BusinessException(parseException, ErrorEnumeration.INPUT_PARAMETER_INCORRECT);
        }
    }

    private static String parseDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }
}
