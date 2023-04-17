package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Test
    public void testGetMedicalRecords() throws Exception {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/01/1980");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord1.setBirthdate("06/06/1986");

        medicalRecordService.addMedicalRecord(medicalRecord1);
        medicalRecordService.addMedicalRecord(medicalRecord2);

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        assertThat(medicalRecords).contains(medicalRecord1, medicalRecord2);
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        MedicalRecord medicalRecordToDelete = new MedicalRecord();
        medicalRecordToDelete.setFirstName("Jonathan");
        medicalRecordToDelete.setLastName("Dote");
        medicalRecordService.addMedicalRecord(medicalRecordToDelete);

        medicalRecordService.deleteMedicalRecord("Jonathan", "Dote");
        MedicalRecord deletedMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName("Jonathan", "Dote");
        assertNull(deletedMedicalRecord);
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord medicalRecordToUpdate = new MedicalRecord();
        medicalRecordToUpdate.setFirstName("Janette");
        medicalRecordToUpdate.setLastName("Doye");
        medicalRecordToUpdate.setBirthdate("06/06/1996");
        medicalRecordService.addMedicalRecord(medicalRecordToUpdate);

        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setFirstName("Janette");
        updatedMedicalRecord.setLastName("Doye");
        updatedMedicalRecord.setBirthdate("06/06/1989");

        medicalRecordService.updateMedicalRecord(updatedMedicalRecord);

        MedicalRecord retrievedMedicalRecord = medicalRecordRepository.findByFirstNameAndLastName("Janette", "Doye");
        assertEquals(updatedMedicalRecord.getBirthdate(), retrievedMedicalRecord.getBirthdate());
    }
}
