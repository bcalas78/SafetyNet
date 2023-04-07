package com.openclassrooms.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class FirestationDTO {
    private List<PersonDTO> persons;
    int adultCount;
    int childCount;
}
