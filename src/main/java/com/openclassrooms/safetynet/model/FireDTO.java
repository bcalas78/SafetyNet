package com.openclassrooms.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class FireDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private List<String> medications;
    private List<String> allergies;
    private int stationNumber;
}
