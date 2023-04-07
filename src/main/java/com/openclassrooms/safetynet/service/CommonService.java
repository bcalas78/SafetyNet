package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonService {

    @Autowired
    private ObjectMapper objectMapper;

    private DateUtils dateUtils = new DateUtils();

    @Autowired
    private FirestationRepository firestationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public CommunityEmailDTO getEmailsByCity(String city) throws IOException {
       Data communityData = objectMapper.readValue(new File("src/main/resources/data.json"), Data.class);
       List<String> emails = communityData.getPersons().stream()
               .filter(person -> person.getCity().equals(city))
               .map(Person::getEmail)
               .collect(Collectors.toList());

       CommunityEmailDTO communityEmailDTO = new CommunityEmailDTO();
       communityEmailDTO.setEmails(emails);
       return communityEmailDTO;
   }

   public List<PhoneAlertDTO> getPhoneAlertByFirestation(String firestationNumber) {
       List<String> addresses = firestationRepository.getAddressesByFirestationNumber(firestationNumber);

       List<PhoneAlertDTO> phoneAlerts = new ArrayList<>();
       for (String address : addresses) {
           List<Person> persons = personRepository.getPersonsByAddress(address);
           for(Person person : persons) {
               PhoneAlertDTO phoneAlert = new PhoneAlertDTO();
               //phoneAlert.setFirstName(person.getFirstName());
               //phoneAlert.setLastName(person.getLastName());
               phoneAlert.setPhone(person.getPhone());
               phoneAlerts.add(phoneAlert);
           }
       }
        return phoneAlerts;
   }

   public List<FireDTO> getFireDTOsByAddress(String address) {
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
   }

   public List<PersonInfoDTO> getPersonInfo(String firstName, String lastName) {
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
   }

   public FirestationDTO getPersonsByFirestationNumber(String stationNumber) {
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

            MedicalRecord medicalRecord = new MedicalRecord();
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
   }
}
