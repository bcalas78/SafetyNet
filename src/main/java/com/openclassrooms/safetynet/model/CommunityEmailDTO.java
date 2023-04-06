package com.openclassrooms.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class CommunityEmailDTO {
    private List<String> emails;
}
