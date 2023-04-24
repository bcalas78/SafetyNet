package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.FirestationDTO;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import com.openclassrooms.safetynet.service.FirestationService;
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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FirestationController firestationController;

    @MockBean
    private DatabaseManipulation databaseManipulation;

    @MockBean
    private FirestationService firestationService;

    @MockBean
    private Data data;

    @MockBean
    private CommonService commonService;

    @Test
    public void testGetFirestations() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonsByFirestationNumberCatch() throws Exception{
        String stationNumber = "2";
        Mockito.when(commonService.getPersonsByFirestationNumber(stationNumber)).thenThrow(new RuntimeException("Mockito Exception"));

        FirestationDTO result = null;
        try {
            result = firestationController.getPersonsByFirestationNumber(stationNumber);
        } catch (Exception e) {
            verify(commonService).getPersonsByFirestationNumber(stationNumber);
            verify(firestationController).getPersonsByFirestationNumber(stationNumber);
            Assert.assertEquals("Request Get persons by firestation number cannot succeed", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddFirestation() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setAddress("1000 King Road");
        firestation.setStation("1");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFirestation = objectMapper.writeValueAsString(firestation);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFirestation))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.address", is("1000 King Road")))
                        .andExpect(jsonPath("$.station", is("1")));
    }

    @Test
    public void testAddFirestationCatch() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setAddress("1000 King Road");
        firestation.setStation("1");

        Mockito.doThrow(new Exception("Mocked exception")).when(firestationService).addFirestation(firestation);
        //when(firestationService.addFirestation(firestation)).thenThrow(new RuntimeException("Mockito Exception"));


        ResponseEntity<Firestation> result = null;
        try {
            result = firestationController.addFirestation(firestation);
        } catch (Exception e) {
            Mockito.verify(firestationService).addFirestation(firestation);
            Mockito.verify(firestationController).addFirestation(firestation);
            Assert.assertEquals("Cannot created firestation", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        Firestation firestationToUpdate = new Firestation();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFirestation = objectMapper.writeValueAsString(firestationToUpdate);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFirestation))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFirestationCatch() throws Exception {
        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setAddress("123 Main St");
        updatedFirestation.setStation("2");
        Mockito.when(firestationService.updateFirestation(updatedFirestation)).thenThrow(new Exception("Mocked Exception"));

        ResponseEntity<Firestation> result = null;

        try {
            result = firestationController.updateFirestation(updatedFirestation);
        } catch (Exception e) {
            Mockito.verify(firestationService).updateFirestation(updatedFirestation);
            Mockito.verify(firestationController).updateFirestation(updatedFirestation);
            Assert.assertEquals("Cannot update firestation", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        Firestation firestationToDelete = new Firestation();
        firestationToDelete.setAddress("1000 King Road");
        firestationToDelete.setStation("1");

        firestationService.addFirestation(firestationToDelete);

        mockMvc.perform(delete("/firestation")
                        .param("address", "1000 King Road")
                        .param("station", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        Mockito.doThrow(new Exception("Mocked Exception")).when(firestationService).deleteFirestation(firstName, lastName);

        try {
            firestationController.deleteFirestation(firstName, lastName);
        } catch (Exception e) {
            Mockito.verify(firestationService).deleteFirestation(firstName, lastName);
            Mockito.verify(firestationController).deleteFirestation(firstName, lastName);
            Assert.assertEquals("Cannot Delete firestation", e.getMessage());
        }
    }
}

