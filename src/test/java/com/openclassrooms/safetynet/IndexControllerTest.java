package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseManipulation databaseManipulation;

    @MockBean
    private CommonService commonService;

    @Test
    public void testGetChildAlertDTOByAddress() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneAlertsByFirestation() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireDTOsByAddress() throws Exception {
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFloodStations() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmailsByCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());
    }
}
