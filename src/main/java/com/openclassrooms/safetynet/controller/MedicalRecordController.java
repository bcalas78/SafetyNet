package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("/medicalRecord")
    public List<MedicalRecord> medicalRecordList() {
        return medicalRecordService.getMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
        return new ResponseEntity<MedicalRecord>(medicalRecord, HttpStatus.CREATED);
    }
}
