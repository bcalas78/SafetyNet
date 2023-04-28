package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DatabaseManipulation {

    private static final Logger logger = LogManager.getLogger(DatabaseManipulation.class);

    @Autowired
    private FirestationRepository firestationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public void init() throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File dataFile = new File("src/main/resources/data.json");
            Data data = objectMapper.readValue((dataFile), Data.class);
            firestationRepository.save(data.getFirestations());
            personRepository.save(data.getPersons());
            medicalRecordRepository.save(data.getMedicalrecords());
            logger.info("Safetynet data " + data);
        } catch (Exception e) {
            logger.error("Cannot init Safetynet Application");
        }
    }
}
