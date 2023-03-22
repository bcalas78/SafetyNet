package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.MedicalRecord;
import lombok.Data;

import java.util.List;

@Data
public abstract class MedicalRecordRepository {
    private List<MedicalRecord> medicalRecords;
    public abstract void save(List<MedicalRecord> medicalRecordList);

}
