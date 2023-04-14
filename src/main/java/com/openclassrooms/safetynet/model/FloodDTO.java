package com.openclassrooms.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class FloodDTO {
    private String firstName;
    private String lastName;
    private int age;
    private String phone;
    private List<String> medications;
    private List<String> allergies;
}
