package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Mock
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

        List<MedicalRecord> medicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);

        when(medicalRecordRepository.getMedicalRecords()).thenReturn(medicalRecords);

        List<MedicalRecord> actualMedicalRecords = medicalRecordService.getMedicalRecords();

        Assert.assertEquals(medicalRecords, actualMedicalRecords);
        Assert.assertEquals(2, medicalRecords.size());
        assertThat(medicalRecords).contains(medicalRecord1, medicalRecord2);

        verify(medicalRecordRepository, times(1)).getMedicalRecords();
    }

    @Test
    public void testGetMedicalRecordsCatch() throws Exception {
        Mockito.when(medicalRecordRepository.getMedicalRecords()).thenThrow(new RuntimeException("Mocked Exception"));

        List<MedicalRecord> result = null;
        try {
            result = medicalRecordService.getMedicalRecords();
        } catch (Exception e) {
            Mockito.verify(medicalRecordRepository).save(result);
            Mockito.verify(medicalRecordService).getMedicalRecords();
            Assert.assertEquals("Cannot get List of medicalRecords", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("01/01/1980");

        doNothing().when(medicalRecordRepository).addMedicalRecord(medicalRecord);
        medicalRecordService.addMedicalRecord(medicalRecord);
        verify(medicalRecordRepository, times(1)).addMedicalRecord(medicalRecord);
    }

    @Test
    public void testAddMedicalRecordCatch() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("01/01/1980");

        doThrow(new RuntimeException()).when(medicalRecordRepository).addMedicalRecord(medicalRecord);
        medicalRecordService.addMedicalRecord(medicalRecord);
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(firstName, lastName);
    }

    @Test
    public void testDeleteMedicalCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.doThrow(new RuntimeException("Cannot Delete medicalRecord")).when(medicalRecordRepository).deleteMedicalRecord(firstName, lastName);

        try {
            medicalRecordService.deleteMedicalRecord(firstName, lastName);
        } catch (Exception e) {
            Assert.assertEquals("Cannot Delete medicalRecord", e.getMessage());
        }
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord existingMedicalRecord = new MedicalRecord();
        existingMedicalRecord.setFirstName("Janette");
        existingMedicalRecord.setLastName("Doe");
        existingMedicalRecord.setBirthdate("06/06/1996");

        when(medicalRecordRepository.findByFirstNameAndLastName("Janette", "Doe")).thenReturn(existingMedicalRecord);

        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setFirstName("Janette");
        updatedMedicalRecord.setLastName("Doe");
        updatedMedicalRecord.setBirthdate("06/06/1989");

        when(medicalRecordRepository.saveAndUpdate(existingMedicalRecord)).thenReturn(updatedMedicalRecord);

        MedicalRecord result = medicalRecordService.updateMedicalRecord(updatedMedicalRecord);

        assertEquals(updatedMedicalRecord, result);

        verify(medicalRecordRepository).saveAndUpdate(existingMedicalRecord);
    }

    @Test
    void testUpdateMedicalRecordCatch() throws Exception {
        String firstName = "Janette";
        String lastName = "Doe";
        String errorMessage = "Cannot update medicalRecord";

        when(medicalRecordRepository.findByFirstNameAndLastName(firstName, lastName)).thenThrow(new RuntimeException(errorMessage));

        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setFirstName(firstName);
        updatedMedicalRecord.setLastName(lastName);

        MedicalRecord result = medicalRecordService.updateMedicalRecord(updatedMedicalRecord);

        assertNull(result);
    }
}
