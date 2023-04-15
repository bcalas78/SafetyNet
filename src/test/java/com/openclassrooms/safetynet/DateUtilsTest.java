package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DateUtilsTest {

    @Test
    public void testCalculateAge() throws Exception {
        String birthdate = "07/16/1996";

        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate currentDate = LocalDate.now();
        int expectedAge = Period.between(birthDate, currentDate).getYears();

        int actualAge = DateUtils.calculateAge(birthdate);
        assertEquals(expectedAge, actualAge);
    }

    @Test
    public void testCalculateAgeWithNullDate() throws Exception {
        String birthDate = null;

        int actualAge = DateUtils.calculateAge(birthDate);
        assertEquals(0, actualAge);
    }
}
