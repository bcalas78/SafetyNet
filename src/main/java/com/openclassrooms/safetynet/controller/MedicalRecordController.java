package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {

    private static final Logger logger = LogManager.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord")
    public List<MedicalRecord> getMedicalRecordList() {
        try {
            logger.info("Request GET: Get medicalRecordList with success.");
            return medicalRecordService.getMedicalRecords();
        } catch (Exception e) {
            logger.error("Request Get medicalRecordList cannot succeed", e);
            return null;
        }
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            logger.info("Request POST: medicalRecord created with success.");
            medicalRecordService.addMedicalRecord(medicalRecord);
            return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Cannot created medicalRecord", e);
            return null;
        }
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecord(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            logger.info("Request DELETE: deleted medicalRecord with success.");
            medicalRecordService.deleteMedicalRecord(firstName, lastName);
        } catch (Exception e) {
            logger.error("Cannot delete medicalRecord", e);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord updatedMedicalRecord) {
        try {
            logger.info("Request PUT: updated medicalRecord with success.");
            MedicalRecord savedMedicalRecord = medicalRecordService.updateMedicalRecord(updatedMedicalRecord);
            return ResponseEntity.ok(savedMedicalRecord);
        } catch (Exception e) {
            logger.error("Cannot update medicalRecord", e);
            return null;
        }
    }
}
