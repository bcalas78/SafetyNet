package com.openclassrooms.safetynet.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final Logger logger = LogManager.getLogger(DateUtils.class);

    public static int calculateAge(String birthdate) throws Exception {
        try {
            logger.debug("Age calculated with success.");
            if (birthdate == null) {
                return 0;
            }

            LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        } catch (Exception e) {
            logger.error("Cannot calculate age", e);
            return -1;
        }
    }
}
