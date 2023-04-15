package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.FirestationController;
import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import com.openclassrooms.safetynet.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}

