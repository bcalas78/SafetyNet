package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.MedicalRecord;
import lombok.Data;

import java.util.List;

@Data
public abstract class MedicalRecordRepository {
    private List<MedicalRecord> medicalRecords;
    public abstract void save(List<MedicalRecord> medicalRecordList);

    private MedicalRecord medicalRecord;

    public void addMedicalRecord(MedicalRecord medicalRecord) {
    }

    public abstract void deleteMedicalRecord(String firstName, String lastName);

    public abstract MedicalRecord saveAndUpdate(MedicalRecord medicalRecord);
    public abstract MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);
}
