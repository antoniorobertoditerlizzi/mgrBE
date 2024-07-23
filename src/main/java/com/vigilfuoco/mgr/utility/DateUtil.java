package com.vigilfuoco.mgr.utility;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class DateUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}