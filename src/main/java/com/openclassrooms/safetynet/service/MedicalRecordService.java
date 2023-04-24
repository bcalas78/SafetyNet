package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private static final Logger logger = LogManager.getLogger(MedicalRecordService.class);

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecords() throws Exception {
        try {
            logger.debug("Get medicalRecords with success.");
            return medicalRecordRepository.getMedicalRecords();
        } catch (Exception e) {
            logger.error("Cannot get List of medicalRecords", e);
            return null;
        }
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) throws Exception {
        try {
            logger.debug("Added medicalRecord with success.");
            medicalRecordRepository.addMedicalRecord(medicalRecord);
        } catch (Exception e) {
            logger.error("Cannot add medicalRecord", e);
        }
    }

    public void deleteMedicalRecord(String firstName, String lastName) throws Exception {
        try {
            logger.debug("Deleted medicalRecord with success.");
            medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
        } catch (Exception e) {
            logger.error("Cannot Delete medicalRecord", e);
        }
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord) throws Exception {
        try {
            logger.debug("Updated medicalRecord with success.");
            MedicalRecord medicalRecordToUpdate = medicalRecordRepository.findByFirstNameAndLastName(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
            medicalRecordToUpdate.setBirthdate(updatedMedicalRecord.getBirthdate());
            medicalRecordToUpdate.setMedications(updatedMedicalRecord.getMedications());
            medicalRecordToUpdate.setAllergies(updatedMedicalRecord.getAllergies());
            return medicalRecordRepository.saveAndUpdate(medicalRecordToUpdate);
        } catch (Exception e) {
            logger.error("Cannot update medicalRecord", e);
            return null;
        }
    }
}
