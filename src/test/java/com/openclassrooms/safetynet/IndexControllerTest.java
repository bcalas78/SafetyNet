package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.IndexController;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseManipulation databaseManipulation;

    @InjectMocks
    private IndexController indexController;

    @MockBean
    private CommonService commonService;

    @Test
    public void testGetChildAlertDTOByAddress() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetChildAlertDTOByAddressCatch() throws Exception {
        String address = "1234 Test St";
        Mockito.when(commonService.getChildrenByAddress(address)).thenThrow(new Exception("Mocked Exception"));

        List<ChildAlertDTO> result = null;
        try {
            result = indexController.getChilAlertDTOByAddress(address);
        } catch (Exception e) {
            Mockito.verify(commonService).getChildrenByAddress(address);
            Mockito.verify(indexController).getChilAlertDTOByAddress(address);
            Assert.assertEquals("Request Get childAlert cannot succeed", e.getMessage());
        }
         Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetPhoneAlertsByFirestation() throws Exception {
        mockMvc.perform(get("/phoneAlert?firestation=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneAlertsByFirestationCatch() throws Exception {
        String firestationNumber = "2";
        Mockito.when(commonService.getPhoneAlertByFirestation(firestationNumber)).thenThrow(new Exception("Mocked Exception"));

        List<PhoneAlertDTO> result = null;
        try {
            result = indexController.getPhoneAlertsByFirestation(firestationNumber);
        } catch (Exception e) {
            Mockito.verify(commonService).getPhoneAlertByFirestation(firestationNumber);
            Mockito.verify(indexController).getPhoneAlertsByFirestation(firestationNumber);
            Assert.assertEquals("Request Get phoneAlert cannot succeed", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetFireDTOsByAddress() throws Exception {
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireDTOsByAddressCatch() throws Exception {
        String address = "1234 Test St";
        Mockito.when(commonService.getFireDTOsByAddress(address)).thenThrow(new Exception("Mocked Exception"));

        List<FireDTO> result = null;
        try {
            result = indexController.getFireDTOsByAddress(address);
        } catch (Exception e) {
            Mockito.verify(commonService).getFireDTOsByAddress(address);
            Mockito.verify(indexController).getFireDTOsByAddress(address);
            Assert.assertEquals("Request Get fire cannot succeed", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetFloodStations() throws Exception {
        mockMvc.perform(get("/flood/stations?stations=1,2"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFloodStationsCatch() throws Exception {
        List<String> stations = Arrays.asList("1","2");
        Mockito.when(commonService.getFloodStations(stations)).thenThrow(new Exception("Mocked Exception"));

        Map<String, List<FloodDTO>> result = null;
        try {
            result = indexController.getFloodStations(stations);
        } catch (Exception e) {
            Mockito.verify(commonService).getFloodStations(stations);
            Mockito.verify(indexController).getFloodStations(stations);
            Assert.assertEquals("Request Get flood/stations cannot succeed", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfoCatch() throws Exception {
        String firstName = "Benjamin";
        String lastName = "Lecomte";
        Mockito.when(commonService.getPersonInfo(firstName,lastName)).thenThrow(new Exception("Mocked Exception"));

        List<PersonInfoDTO> result = null;
        try {
            result = indexController.getPersonInfo(firstName,lastName);
        } catch (Exception e) {
            Mockito.verify(commonService).getPersonInfo(firstName,lastName);
            Mockito.verify(indexController).getPersonInfo(firstName,lastName);
            Assert.assertEquals("Request Get personInfo cannot succeed", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetEmailsByCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmailsByCityCatch() throws Exception {
        String city = "Nice";
        Mockito.when(commonService.getEmailsByCity(city)).thenThrow(new Exception("Mocked Exception"));

        ResponseEntity<CommunityEmailDTO> result = null;
        try {
            result = indexController.getEmailsByCity(city);
        } catch (Exception e) {
            Mockito.verify(commonService).getEmailsByCity(city);
            Mockito.verify(indexController).getEmailsByCity(city);
            Assert.assertEquals("Request Get communityEmail cannot succeed", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

}
