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

    @Override
    public void deleteMedicalRecord(String firstName, String lastName){
        List<MedicalRecord> medicalRecordtemporaryList = this.getMedicalRecords();
        for ( MedicalRecord medicalRecord: medicalRecordtemporaryList ) {
            if(medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                this.getMedicalRecords().remove(medicalRecord);
                break;
            }
        }
    }

    @Override
    public MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        List<MedicalRecord> medicalRecordtemporaryList = this.getMedicalRecords();
        for (MedicalRecord medicalRecord : medicalRecordtemporaryList) {
            if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
                return medicalRecord;
            }
        }
        return null;
    }

    @Override
    public MedicalRecord saveAndUpdate(MedicalRecord updatedMedicalRecord) {
        List<MedicalRecord> medicalRecordtemporaryList = this.getMedicalRecords();
        return updatedMedicalRecord;
    }
}
