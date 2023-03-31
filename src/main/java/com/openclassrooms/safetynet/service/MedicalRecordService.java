package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.getMedicalRecords();
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.addMedicalRecord(medicalRecord);
    }
}
