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

    public void deleteMedicalRecord(String firstName, String lastName){
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }

    public MedicalRecord updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
        MedicalRecord medicalRecordToUpdate = medicalRecordRepository.findByFirstNameAndLastName(updatedMedicalRecord.getFirstName(), updatedMedicalRecord.getLastName());
        medicalRecordToUpdate.setBirthdate(updatedMedicalRecord.getBirthdate());
        medicalRecordToUpdate.setMedications(updatedMedicalRecord.getMedications());
        medicalRecordToUpdate.setAllergies(updatedMedicalRecord.getAllergies());

        return medicalRecordRepository.saveAndUpdate(medicalRecordToUpdate);
    }
}
