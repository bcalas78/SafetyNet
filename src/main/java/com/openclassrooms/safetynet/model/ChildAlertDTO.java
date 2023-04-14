package com.openclassrooms.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class ChildAlertDTO {
    private String firstname;
    private String lastName;
    private int age;
    private List<String> otherFamilyMembers;
}
