package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.FirestationDTO;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private Data data;

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private CommonService commonService;

    //@GetMapping("/firestation")
    //public List<Firestation> firestationsList() {
        //return firestationService.getFirestations();
    //}

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {
        firestationService.addFirestation(firestation);
        return new ResponseEntity<Firestation>(firestation, HttpStatus.CREATED);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam("station" ) String station, @RequestParam("address") String address) {
        firestationService.deleteFirestation(station, address);
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation updatedFirestation) {
        Firestation savedFirestation = firestationService.updateFirestation(updatedFirestation);
        return ResponseEntity.ok(savedFirestation);
    }

    @GetMapping("/firestation")
    public FirestationDTO getPersonsByFirestationNumber(@RequestParam String stationNumber) {
        return commonService.getPersonsByFirestationNumber(stationNumber);
    }

}

