package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping("/firestation")
    public List<Firestation> firestationsList() {
     return firestationService.getFirestations();
    }

}

