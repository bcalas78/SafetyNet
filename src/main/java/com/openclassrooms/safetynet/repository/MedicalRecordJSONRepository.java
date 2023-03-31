package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.MedicalRecord;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class MedicalRecordJSONRepository extends MedicalRecordRepository {

    @Override
    public void save(List<MedicalRecord> medicalRecordList) {
        this.setMedicalRecords(medicalRecordList);
    }

    @Override
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        this.getMedicalRecords().add(medicalRecord);
    }
}
