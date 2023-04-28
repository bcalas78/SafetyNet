package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.FirestationDTO;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {

    private static final Logger logger = LogManager.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private CommonService commonService;

    @GetMapping("/firestation")
    public FirestationDTO getPersonsByFirestationNumber(@RequestParam String stationNumber) {
        try {
            logger.info("Request GET: Get persons by firestation with success.");
            return commonService.getPersonsByFirestationNumber(stationNumber);
        } catch(Exception e) {
            logger.error("Request Get persons by firestation number cannot succeed", e);
            return null;
        }
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {

        try {
            logger.info("Request POST: Firestation created with success.");
            firestationService.addFirestation(firestation);
            return new ResponseEntity<Firestation>(firestation, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Cannot created firestation", e);
            return null;
        }
    }

    @DeleteMapping("/firestation")
    public void deleteFirestation(@RequestParam("station") String station, @RequestParam("address") String address) {
        try {
            logger.info("Request DELETE: deleted firestation with success.");
            firestationService.deleteFirestation(station, address);
        } catch (Exception e) {
            logger.error("Cannot delete firestation", e);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation updatedFirestation) {
        try {
            logger.info("Request PUT: updated firestation with success.");
            Firestation savedFirestation = firestationService.updateFirestation(updatedFirestation);
            return ResponseEntity.ok(savedFirestation);
        } catch (Exception e) {
            logger.error("Cannot update firestation", e);
            return null;
        }
    }
}

