package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.MedicalRecordController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testMedicalRecordListCatch() throws Exception {
        Mockito.when(medicalRecordService.getMedicalRecords()).thenThrow(new RuntimeException("Mockito Exception"));

        List<MedicalRecord> result = null;
        try {
            result = medicalRecordController.medicalRecordList();
        } catch (Exception e) {
            Mockito.verify(medicalRecordService).getMedicalRecords();
            Mockito.verify(medicalRecordController).medicalRecordList();
            Assert.assertEquals("Request Get medicalRecordList cannot succeed", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddMedicalRecord() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPerson = objectMapper.writeValueAsString(medicalRecord);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.firstName", is("John")))
                        .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testAddMedicalRecordCatch() throws Exception {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");

        Mockito.doThrow(new Exception("Mocked Exception")).when(medicalRecordService).addMedicalRecord(medicalRecord);

        ResponseEntity<MedicalRecord> result = null;
        try {
            result = medicalRecordController.addMedicalRecord(medicalRecord);
        } catch (Exception e) {
            Mockito.verify(medicalRecordService).addMedicalRecord(medicalRecord);
            Mockito.verify(medicalRecordController).addMedicalRecord(medicalRecord);
            Assert.assertEquals("Cannot created medicalRecord", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        MedicalRecord medicalRecordToUpdate = new MedicalRecord();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPerson = objectMapper.writeValueAsString(medicalRecordToUpdate);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMedicalRecordCatch() throws Exception {
        MedicalRecord medicalRecordToUpdate = new MedicalRecord();
        medicalRecordToUpdate.setFirstName("John");
        medicalRecordToUpdate.setLastName("Doe");
        Mockito.when(medicalRecordService.updateMedicalRecord(medicalRecordToUpdate)).thenThrow(new Exception("Mocked Exception"));

        ResponseEntity<MedicalRecord> result = null;
        try {
            result = medicalRecordController.updateMedicalRecord(medicalRecordToUpdate);
        } catch (Exception e) {
            Mockito.verify(medicalRecordService).updateMedicalRecord(medicalRecordToUpdate);
            Mockito.verify(medicalRecordController).updateMedicalRecord(medicalRecordToUpdate);
            Assert.assertEquals("Cannot update medicalRecord", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testDeleteMedicalRecord() throws Exception {
        MedicalRecord medicalRecordToDelete = new MedicalRecord();
        medicalRecordToDelete.setFirstName("John");
        medicalRecordToDelete.setLastName("Doe");

        medicalRecordService.addMedicalRecord(medicalRecordToDelete);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMedicalRecordCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        Mockito.doThrow(new Exception("Mocked Exception")).when(medicalRecordService).deleteMedicalRecord(firstName, lastName);

        try {
            medicalRecordController.deleteMedicalRecord(firstName, lastName);
        } catch (Exception e) {
            Mockito.verify(medicalRecordService).deleteMedicalRecord(firstName, lastName);
            Mockito.verify(medicalRecordController).deleteMedicalRecord(firstName, lastName);
            Assert.assertEquals("Cannot delete medicalRecord", e.getMessage());
        }
    }

}
