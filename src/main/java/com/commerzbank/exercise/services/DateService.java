package com.commerzbank.exercise.services;

import com.commerzbank.exercise.utilities.DateUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class DateService {

    public String getCurrentDate() {
        return DateUtils.formatTimeToUTC1inYYYYMMdd(LocalDateTime.now());
    }
}
