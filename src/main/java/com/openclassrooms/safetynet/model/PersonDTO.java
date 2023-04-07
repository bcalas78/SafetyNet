package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class PersonDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
