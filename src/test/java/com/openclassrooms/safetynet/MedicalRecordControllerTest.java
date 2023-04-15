package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
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

}
