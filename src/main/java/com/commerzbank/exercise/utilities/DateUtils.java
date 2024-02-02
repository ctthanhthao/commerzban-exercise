package com.commerzbank.exercise.utilities;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static OffsetDateTime convertFromLDTtoUTC1(LocalDateTime localDateTime) {
        ZoneOffset zoneOffset = ZoneOffset.of("+01:00");
        return convertFromLDT(localDateTime, zoneOffset);
    }

    public static OffsetDateTime convertFromLDT(LocalDateTime localDateTime, ZoneOffset offset) {
        return OffsetDateTime.of(localDateTime, offset);
    }

    public static String formatTimeToUTC1inYYYYMMdd(LocalDateTime ldt) {
        String formatPattern = "yyyy-MM-dd";
        OffsetDateTime odt = convertFromLDTtoUTC1(ldt);
        return odt.format(DateTimeFormatter.ofPattern(formatPattern));
    }
}
