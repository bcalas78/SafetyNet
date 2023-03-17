package com.openclassrooms.safetynet.model;

import org.springframework.stereotype.Component;

import java.util.List;

@lombok.Data
@Component
public class Data {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

}
