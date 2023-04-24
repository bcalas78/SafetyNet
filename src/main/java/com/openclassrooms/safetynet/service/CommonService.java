package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommonService {

    private static final Logger logger = LogManager.getLogger(CommonService.class);

    @Autowired
    private ObjectMapper objectMapper;

    private DateUtils dateUtils = new DateUtils();

    @Autowired
    private FirestationRepository firestationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public FirestationDTO getPersonsByFirestationNumber(String stationNumber) throws Exception {
        try {
            logger.debug("Get persons by firestation number with success.");

            List<Person> persons = personRepository.getPersonByFirestationNumber(stationNumber);
            int adultCount = 0;
            int chilCount = 0;
            List<PersonDTO> personDTOs = new ArrayList<>();

            for (Person person : persons) {
                PersonDTO personDTO = new PersonDTO();
                personDTO.setFirstName(person.getFirstName());
                personDTO.setLastName(person.getLastName());
                personDTO.setAddress(person.getAddress());
                personDTO.setPhone(person.getPhone());

                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (dateUtils.calculateAge(medicalRecord.getBirthdate()) <= 18) {
                    chilCount++;
                } else {
                    adultCount++;
                }

                personDTOs.add(personDTO);
            }

            FirestationDTO firestationDTO = new FirestationDTO();
            firestationDTO.setPersons(personDTOs);
            firestationDTO.setAdultCount(adultCount);
            firestationDTO.setChildCount(chilCount);

            return firestationDTO;
        } catch (Exception e) {
            logger.error("Cannot get persons by firestation number", e);
            return null;
        }
    }

    public List<ChildAlertDTO> getChildrenByAddress(String address) throws Exception {
        try {
            logger.debug("Get children by address with success.");

            List<Person> persons = personRepository.getPersonsByAddress(address);
            List<ChildAlertDTO> children = new ArrayList<>();

            for (Person person : persons) {
                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (dateUtils.calculateAge(medicalRecord.getBirthdate()) <= 18) {
                    ChildAlertDTO child = new ChildAlertDTO();
                    child.setFirstname(person.getFirstName());
                    child.setLastName(person.getLastName());
                    child.setAge(dateUtils.calculateAge(medicalRecord.getBirthdate()));

                    List<String> otherFamilyMembers = new ArrayList<>();
                    for (Person member : persons) {
                        if (!member.equals(person)) {
                            otherFamilyMembers.add(member.getFirstName() + " " + member.getLastName());
                        }
                    }
                    child.setOtherFamilyMembers(otherFamilyMembers);

                    children.add(child);
                }
            }
            return children;
        } catch (Exception e) {
            logger.error("Cannot get children by address", e);
            return null;
        }
    }

    public List<PhoneAlertDTO> getPhoneAlertByFirestation(String firestationNumber) throws Exception {
        try {
            logger.debug("Get phone alert by firestation with success.");

            List<String> addresses = firestationRepository.getAddressesByFirestationNumber(firestationNumber);

            List<PhoneAlertDTO> phoneAlerts = new ArrayList<>();
            for (String address : addresses) {
                List<Person> persons = personRepository.getPersonsByAddress(address);
                for(Person person : persons) {
                    PhoneAlertDTO phoneAlert = new PhoneAlertDTO();
                    phoneAlert.setPhone(person.getPhone());
                    phoneAlerts.add(phoneAlert);
                }
            }
            return phoneAlerts;
        } catch (Exception e) {
            logger.error("Cannot get phone alert by firestation", e);
            return null;
        }
    }

    public List<FireDTO> getFireDTOsByAddress(String address) throws Exception {
        try {
            logger.debug("Get fire DTOs by address with success.");

            List<FireDTO> fireDTOS = new ArrayList<>();
            int stationNumber = firestationRepository.getStationNumberByAddress(address);
            if (stationNumber != -1) {
                List<Person> personsAtAddress = personRepository.getPersonsByAddress(address);
                for (Person person : personsAtAddress) {
                    FireDTO fireDTO = new FireDTO();
                    fireDTO.setFirstName(person.getFirstName());
                    fireDTO.setLastName(person.getLastName());
                    fireDTO.setPhone(person.getPhone());
                    MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    if (medicalRecord != null) {
                        fireDTO.setAge(dateUtils.calculateAge(medicalRecord.getBirthdate()));
                        fireDTO.setMedications(medicalRecord.getMedications());
                        fireDTO.setAllergies(medicalRecord.getAllergies());
                    }
                    fireDTO.setStationNumber(stationNumber);
                    fireDTOS.add(fireDTO);
                }
            }
            return fireDTOS;
        } catch (Exception e) {
            logger.error("Cannot get fire DTOs by address", e);
            return null;
        }
    }

    public Map<String, List<FloodDTO>> getFloodStations(List<String> stationNumbers) throws Exception {
        try {
            logger.debug("Get flood stations with success.");

            Map<String, List<FloodDTO>> floodStationResult = new HashMap<>();
            for (String stationNumber : stationNumbers) {
                List<Person> persons = personRepository.getPersonByFirestationNumber(stationNumber);
                for (Person person : persons) {
                    String address = person.getAddress();
                    if (!floodStationResult.containsKey(address)) {
                        floodStationResult.put(address, new ArrayList<>());
                    }
                    FloodDTO floodDTO = new FloodDTO();
                    floodDTO.setFirstName(person.getFirstName());
                    floodDTO.setLastName(person.getLastName());
                    floodDTO.setPhone(person.getPhone());
                    MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    if (medicalRecord != null) {
                        floodDTO.setAge(dateUtils.calculateAge(medicalRecord.getBirthdate()));
                        floodDTO.setMedications(medicalRecord.getMedications());
                        floodDTO.setAllergies(medicalRecord.getAllergies());
                    }
                    floodStationResult.get(address).add(floodDTO);
                }
            }
            return floodStationResult;
        } catch (Exception e) {
            logger.error("Cannot get flood stations", e);
            return null;
        }
    }

    public List<PersonInfoDTO> getPersonInfo(String firstName, String lastName) throws Exception {
        try {
            logger.debug("Get person info success.");

            List<PersonInfoDTO> personInfoDTOList = new ArrayList<>();

            List<Person> personByFirstNameAndLastName = personRepository.getPersonByFirstNameAndLastName(firstName, lastName);
            for (Person person : personByFirstNameAndLastName) {
                PersonInfoDTO personInfoDTO = new PersonInfoDTO();
                personInfoDTO.setFirstName(person.getFirstName());
                personInfoDTO.setLastName(person.getLastName());
                personInfoDTO.setAddress(person.getAddress());
                personInfoDTO.setEmail(person.getEmail());
                MedicalRecord medicalRecord = medicalRecordRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                if (medicalRecord != null) {
                    personInfoDTO.setAge(dateUtils.calculateAge(medicalRecord.getBirthdate()));
                    personInfoDTO.setMedications(medicalRecord.getMedications());
                    personInfoDTO.setAllergies(medicalRecord.getAllergies());
                }
                personInfoDTOList.add(personInfoDTO);
            }
            return personInfoDTOList;
        } catch (Exception e) {
            logger.error("Cannot get person info", e);
            return null;
        }
    }

    public CommunityEmailDTO getEmailsByCity(String city) throws Exception {
        try {
            logger.debug("Get emails by city with success.");

            Data communityData = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
            List<String> emails = communityData.getPersons().stream()
                    .filter(person -> person.getCity().equals(city))
                    .map(Person::getEmail)
                    .collect(Collectors.toList());

            CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
            communityEmailDTO.setEmails(emails);
            return communityEmailDTO;
        } catch (Exception e) {
            logger.error("Cannot get emails by city", e);
            return null;
        }
   }
}
