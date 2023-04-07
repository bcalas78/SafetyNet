package com.openclassrooms.safetynet.utils;

import com.openclassrooms.safetynet.model.MedicalRecord;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static int calculateAge(String birthdate) {
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate currentDate = LocalDate.now();
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
