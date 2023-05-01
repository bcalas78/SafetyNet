package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.CommonService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {
    @InjectMocks
    private CommonService commonService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private FirestationRepository firestationRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    public void testGetPersonsByFirestationNumber() throws Exception {
        String stationNumber = "1";

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("1000 King Road");
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johndoe@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("1000 King Road");
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-5555");
        person2.setEmail("janedoe@email.com");

        List<Person> persons = Arrays.asList(person1, person2);

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/01/2000");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord1.setBirthdate("06/06/1986");

        when(personRepository.getPersonByFirestationNumber(stationNumber)).thenReturn(persons);
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(medicalRecord1);
        when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(medicalRecord2);

        FirestationDTO firestationDTO = commonService.getPersonsByFirestationNumber(stationNumber);

        assertNotNull(firestationDTO);
        assertEquals(2, firestationDTO.getPersons().size());
        assertEquals(1, firestationDTO.getAdultCount());
        assertEquals(1, firestationDTO.getChildCount());

        verify(personRepository, times(1)).getPersonByFirestationNumber(stationNumber);
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
        verify(medicalRecordRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    public void testGetPersonsByFirestationNumberCatch() throws Exception {
        String stationNumber = "1";
        Mockito.when(commonService.getPersonsByFirestationNumber(stationNumber)).thenThrow(new RuntimeException("Mocked Exception"));

        FirestationDTO result = null;
        try {
            result = commonService.getPersonsByFirestationNumber(stationNumber);
        } catch (Exception e) {
            Mockito.verify(commonService).getPersonsByFirestationNumber(stationNumber);
            Assert.assertEquals("Cannot get persons by firestation number", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetChildrenByAddress() throws Exception {
        Person adult1 = new Person();
        adult1.setFirstName("Jonathan");
        adult1.setLastName("Doe");
        adult1.setAddress("123 Main St");
        adult1.setCity("London");
        adult1.setZip("1000");
        adult1.setPhone("777-777-7777");
        adult1.setEmail("jonathandoe@email.com");

        MedicalRecord adult1MedicalRecord = new MedicalRecord();
        adult1MedicalRecord.setFirstName("Jonathan");
        adult1MedicalRecord.setLastName("Doe");
        adult1MedicalRecord.setBirthdate("01/01/1980");
        adult1MedicalRecord.setMedications(null);
        adult1MedicalRecord.setAllergies(null);

        Person adult2 = new Person();
        adult2.setFirstName("Jannette");
        adult2.setLastName("Doe");
        adult2.setAddress("123 Main St");
        adult2.setCity("London");
        adult2.setZip("1000");
        adult2.setPhone("777-888-8888");
        adult2.setEmail("jannettedoe@email.com");

        MedicalRecord adult2MedicalRecord = new MedicalRecord();
        adult2MedicalRecord.setFirstName("Jannette");
        adult2MedicalRecord.setLastName("Doe");
        adult2MedicalRecord.setBirthdate("01/01/1985");
        adult2MedicalRecord.setMedications(null);
        adult2MedicalRecord.setAllergies(null);

        Person child1 = new Person();
        child1.setFirstName("Johnny");
        child1.setLastName("Doe");
        child1.setAddress("123 Main St");
        child1.setCity("London");
        child1.setZip("1000");
        child1.setPhone("777-888-9999");
        child1.setEmail("johnnydoe@email.com");

        MedicalRecord child1MedicalRecord = new MedicalRecord();
        child1MedicalRecord.setFirstName("Johnny");
        child1MedicalRecord.setLastName("Doe");
        child1MedicalRecord.setBirthdate("01/01/2010");
        child1MedicalRecord.setMedications(null);
        child1MedicalRecord.setAllergies(null);

        Person child2 = new Person();
        child2.setFirstName("Jannie");
        child2.setLastName("Doe");
        child2.setAddress("123 Main St");
        child2.setCity("London");
        child2.setZip("1000");
        child2.setPhone("777-888-9988");
        child2.setEmail("janniedoe@email.com");

        MedicalRecord child2MedicalRecord = new MedicalRecord();
        child2MedicalRecord.setFirstName("Jannie");
        child2MedicalRecord.setLastName("Doe");
        child2MedicalRecord.setBirthdate("01/01/2015");
        child2MedicalRecord.setMedications(null);
        child2MedicalRecord.setAllergies(null);

        when(personRepository.getPersonsByAddress("123 Main St")).thenReturn(Arrays.asList(adult1, adult2, child1, child2));
        when(medicalRecordRepository.findByFirstNameAndLastName("Jonathan", "Doe")).thenReturn(adult1MedicalRecord);
        when(medicalRecordRepository.findByFirstNameAndLastName("Jannette", "Doe")).thenReturn(adult2MedicalRecord);
        when(medicalRecordRepository.findByFirstNameAndLastName("Johnny", "Doe")).thenReturn(child1MedicalRecord);
        when(medicalRecordRepository.findByFirstNameAndLastName("Jannie", "Doe")).thenReturn(child2MedicalRecord);

        List<ChildAlertDTO> result = commonService.getChildrenByAddress("123 Main St");

        assertEquals(2, result.size());

        ChildAlertDTO childAlert1 = result.get(0);
        assertEquals("Johnny", childAlert1.getFirstname());
        assertEquals("Doe", childAlert1.getLastName());
        assertEquals(13, childAlert1.getAge());
        assertEquals(Arrays.asList("Jonathan Doe", "Jannette Doe","Jannie Doe"), childAlert1.getOtherFamilyMembers());

        ChildAlertDTO childAlert2 = result.get(1);
        assertEquals("Jannie", childAlert2.getFirstname());
        assertEquals("Doe", childAlert2.getLastName());
        assertEquals(8, childAlert2.getAge());
        assertEquals(Arrays.asList("Jonathan Doe", "Jannette Doe", "Johnny Doe"), childAlert2.getOtherFamilyMembers());
    }

    @Test
    public void testGetChildrenByAddressCatch() throws Exception {
        String address = "98 Test St";
        Mockito.when(commonService.getChildrenByAddress(address)).thenThrow(new RuntimeException("Mocked Exception"));

        List<ChildAlertDTO> result = null;
        try {
            result = commonService.getChildrenByAddress(address);
        } catch (Exception e) {
            Mockito.verify(commonService).getChildrenByAddress(address);
            Assert.assertEquals("Cannot get children by address", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetPhoneAlertByFirestation() throws Exception {
        String firestationNumber = "1";
        List<String> addresses = Arrays.asList("123 Main St", "456 Elm St");

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("123 Main St");
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johndoe@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("456 Elm St");
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-6666");
        person2.setEmail("janedoe@email.com");

        when(firestationRepository.getAddressesByFirestationNumber(firestationNumber)).thenReturn(addresses);
        when(personRepository.getPersonsByAddress("123 Main St")).thenReturn(Arrays.asList(person1));
        when(personRepository.getPersonsByAddress("456 Elm St")).thenReturn(Arrays.asList(person2));

        List<PhoneAlertDTO> result = commonService.getPhoneAlertByFirestation(firestationNumber);

        assertEquals(2, result.size());

        PhoneAlertDTO phoneAlert1 = result.get(0);
        assertEquals("555-555-5555", phoneAlert1.getPhone());

        PhoneAlertDTO phoneAlert2 = result.get(1);
        assertEquals("444-555-6666", phoneAlert2.getPhone());
    }

    @Test
    public void testGetPhoneAlertByFirestationCatch() throws Exception {
        String firestationNumber = "1";
        Mockito.when(commonService.getPhoneAlertByFirestation(firestationNumber)).thenThrow(new RuntimeException("Mocked Exception"));

        List<PhoneAlertDTO> result = null;
        try {
            result = commonService.getPhoneAlertByFirestation(firestationNumber);
        } catch (Exception e) {
            Mockito.verify(commonService).getPhoneAlertByFirestation(firestationNumber);
            Assert.assertEquals("Cannot get phone alert by firestation", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetFireDTOsByAddress() throws Exception {
        String address = "1000 King Road";
        int stationNumber = 2;

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("1000 King Road");
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johndoe@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("1000 King Road");
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-6666");
        person2.setEmail("janedoe@email.com");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        medicalRecord1.setBirthdate("01/01/1985");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        medicalRecord2.setBirthdate("02/02/1990");

        when(firestationRepository.getStationNumberByAddress(address)).thenReturn(stationNumber);
        when(personRepository.getPersonsByAddress(address)).thenReturn(Arrays.asList(person1, person2));
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(medicalRecord1);
        when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(medicalRecord2);

        List<FireDTO> result = commonService.getFireDTOsByAddress(address);

        assertEquals(2, result.size());

        FireDTO fireDTO1 = result.get(0);
        assertEquals("John", fireDTO1.getFirstName());
        assertEquals("Doe", fireDTO1.getLastName());
        assertEquals("555-555-5555", fireDTO1.getPhone());
        assertEquals(38, fireDTO1.getAge());
        assertEquals(stationNumber, fireDTO1.getStationNumber());

        FireDTO fireDTO2 = result.get(1);
        assertEquals("Jane", fireDTO2.getFirstName());
        assertEquals("Doe", fireDTO2.getLastName());
        assertEquals("444-555-6666", fireDTO2.getPhone());
        assertEquals(33, fireDTO2.getAge());
        assertEquals(stationNumber, fireDTO2.getStationNumber());
    }

    @Test
    public void testGetFireDTOsByAddressCatch() throws Exception {
        String address = "123 Main St";
        Mockito.when(commonService.getFireDTOsByAddress(address)).thenThrow(new RuntimeException("Mocked Exception"));

        List<FireDTO> result = null;
        try {
            result = commonService.getFireDTOsByAddress(address);
        } catch (Exception e) {
            Mockito.verify(commonService).getFireDTOsByAddress(address);
            Assert.assertEquals("Cannot get fire DTOs by address", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetFloodStations() throws Exception {
        List<String> stationNumbers = Arrays.asList("1", "2");
        String address1 = "123 Main St";
        String address2 = "456 Queen St";

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Smith");
        person1.setAddress(address1);
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johnsmith@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setAddress(address1);
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-6666");
        person2.setEmail("janesmith@email.com");

        Person person3 = new Person();
        person3.setFirstName("Aaron");
        person3.setLastName("Ramsey");
        person3.setAddress(address2);
        person3.setCity("London");
        person3.setZip("1000");
        person3.setPhone("333-444-5555");
        person3.setEmail("aaronramsey@email.com");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Smith");
        medicalRecord1.setBirthdate("01/01/1985");

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Smith");
        medicalRecord2.setBirthdate("02/02/1990");

        MedicalRecord medicalRecord3 = new MedicalRecord();
        medicalRecord3.setFirstName("Aaron");
        medicalRecord3.setLastName("Ramsey");
        medicalRecord3.setBirthdate("12/26/1990");

        when(personRepository.getPersonByFirestationNumber("1")).thenReturn(Arrays.asList(person1, person2));
        when(personRepository.getPersonByFirestationNumber("2")).thenReturn(Arrays.asList(person3));
        when(medicalRecordRepository.findByFirstNameAndLastName("John", "Smith")).thenReturn(medicalRecord1);
        when(medicalRecordRepository.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(medicalRecord2);
        when(medicalRecordRepository.findByFirstNameAndLastName("Aaron", "Ramsey")).thenReturn(medicalRecord3);

        Map<String, List<FloodDTO>> result = commonService.getFloodStations(stationNumbers);

        assertEquals(2, result.size());

        List<FloodDTO> floodDTOs1 = result.get(address1);
        assertEquals(2, floodDTOs1.size());

        FloodDTO floodDTO1 = floodDTOs1.get(0);
        assertEquals("John", floodDTO1.getFirstName());
        assertEquals("Smith", floodDTO1.getLastName());
        assertEquals("555-555-5555", floodDTO1.getPhone());
        assertEquals(38, floodDTO1.getAge());

        FloodDTO floodDTO2 = floodDTOs1.get(1);
        assertEquals("Jane", floodDTO2.getFirstName());
        assertEquals("Smith", floodDTO2.getLastName());
        assertEquals("444-555-6666", floodDTO2.getPhone());
        assertEquals(33, floodDTO2.getAge());

        List<FloodDTO> floodDTOs2 = result.get(address2);
        assertEquals(1, floodDTOs2.size());

        FloodDTO floodDTO3 = floodDTOs2.get(0);
        assertEquals("Aaron", floodDTO3.getFirstName());
        assertEquals("Ramsey", floodDTO3.getLastName());
        assertEquals("333-444-5555", floodDTO3.getPhone());
        assertEquals(32, floodDTO3.getAge());
    }

    @Test
    public void testGetFloodStationsCatch() throws Exception {
        List<String> stationNumbers = Arrays.asList("1","3");
        Mockito.when(commonService.getFloodStations(stationNumbers)).thenThrow(new RuntimeException("Mocked Exception"));

        Map<String, List<FloodDTO>> result = null;
        try {
            result = commonService.getFloodStations(stationNumbers);
        } catch (Exception e) {
            Mockito.verify(commonService).getFloodStations(stationNumbers);
            Assert.assertEquals("Cannot get flood stations", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        String firstName = "Kevin";
        String lastName = "Volland";

        Person person1 = new Person();
        person1.setFirstName(firstName);
        person1.setLastName(lastName);
        person1.setAddress("1919 Munegu St");
        person1.setCity("Monaco");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("kevinvolland@email.com");

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Kevin");
        medicalRecord1.setLastName("Volland");
        medicalRecord1.setBirthdate("07/30/1992");

        when(personRepository.getPersonByFirstNameAndLastName("Kevin", "Volland")).thenReturn(Arrays.asList(person1));
        when(personRepository.getPersons()).thenReturn(Arrays.asList(person1));
        when(medicalRecordRepository.findByFirstNameAndLastName("Kevin", "Volland")).thenReturn(medicalRecord1);

        List<PersonInfoDTO> result = commonService.getPersonInfo("Kevin", "Volland");

        assertNotNull(result);
        assertEquals(1, result.size());
        PersonInfoDTO personInfoDTO = result.get(0);
        assertEquals(firstName, personInfoDTO.getFirstName());
        assertEquals(lastName, personInfoDTO.getLastName());
        assertEquals(person1.getAddress(), personInfoDTO.getAddress());
        assertEquals(person1.getEmail(), personInfoDTO.getEmail());
        assertEquals(30, personInfoDTO.getAge());
        assertEquals(medicalRecord1.getMedications(), personInfoDTO.getMedications());
        assertEquals(medicalRecord1.getAllergies(), personInfoDTO.getAllergies());

        verify(personRepository).getPersonByFirstNameAndLastName(firstName, lastName);
        verify(medicalRecordRepository).findByFirstNameAndLastName(firstName, lastName);
    }

    @Test
    public void testGetPersonInfoCatch() throws Exception {
        String firstName = "Ludovic";
        String lastName = "Giuly";
        Mockito.when(commonService.getPersonInfo(firstName,lastName)).thenThrow(new RuntimeException("Mocked Exception"));

        List<PersonInfoDTO> result = null;
        try {
            result = commonService.getPersonInfo(firstName,lastName);
        } catch (Exception e) {
            Mockito.verify(commonService).getPersonInfo(firstName,lastName);
            Assert.assertEquals("Cannot get person info", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testGetEmailsByCity() throws Exception {
        String city = "London";

        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Smith");
        person1.setAddress("123 Main St");
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johnsmith@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setAddress("123 Main St");
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-6666");
        person2.setEmail("janesmith@email.com");

        Person person3 = new Person();
        person3.setFirstName("Aaron");
        person3.setLastName("Ramsey");
        person3.setAddress("100 Allianz Road");
        person3.setCity("Nice");
        person3.setZip("1000");
        person3.setPhone("333-444-5555");
        person3.setEmail("aaronramsey@email.com");

        Data communityData = new Data();
        communityData.setPersons(Arrays.asList(person1, person2, person3));
        File file = mock(File.class);

        when(objectMapper.readValue(any(File.class), eq(Data.class))).thenReturn(communityData);

        CommunityEmailDTO result = commonService.getEmailsByCity(city);

        assertNotNull(result);
        List<String> expectedEmails = Arrays.asList("johnsmith@email.com", "janesmith@email.com");
        assertEquals(expectedEmails, result.getEmails());
    }

    @Test
    public void testGetEmailsByCityCatch() throws Exception {
        String city = "Prague";
        Mockito.when(commonService.getEmailsByCity(city)).thenThrow(new RuntimeException("Mocked Exception"));

        CommunityEmailDTO result = null;
        try {
            result = commonService.getEmailsByCity(city);
        } catch (Exception e) {
            Mockito.verify(commonService).getEmailsByCity(city);
            Assert.assertEquals("Cannot get emails by city", e.getMessage());
        }
        Assert.assertNull("Result should be null", result);
    }

}
