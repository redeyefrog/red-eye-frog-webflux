package org.redeyefrog.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";

    public static String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return dateTime.format(formatter);
    }

}
