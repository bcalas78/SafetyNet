package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommonService {

    private final FirestationService firestationService;
    private final PersonService personService;

    public CommonService(FirestationService firestationService, PersonService personService) {
        this.firestationService = firestationService;
        this.personService = personService;
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FirestationRepository firestationRepository;

    @Autowired
    private PersonRepository personRepository;

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

}
