package com.commerzbank.exercise.utilities;

import com.commerzbank.exercise.utilities.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class DateUtilsUnitTests {

    private final LocalDateTime ldt =  LocalDateTime.of(2021, 3, 14, 10, 20);

    @Test
    public void convertLocalDateTimeToOffsetDateTime() {
        OffsetDateTime odt = DateUtils.convertFromLDTtoUTC1(ldt);
        Assertions.assertEquals(odt.getOffset().getId(), "+01:00");
    }

    @Test
    public void formatLocalDateTimeInYYYYMMdd() {
        String dateStr = DateUtils.formatTimeToUTC1inYYYYMMdd(ldt);
        Assertions.assertEquals(dateStr, "2021-03-14");
    }
}
