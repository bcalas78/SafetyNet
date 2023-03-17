package com.openclassrooms.safetynet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Data;
import com.openclassrooms.safetynet.model.Person;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class DatabaseManipulation {
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File dataFile = new File("src/main/resources/data.json");
        Data data = objectMapper.readValue((dataFile), Data.class);
        System.out.println(data);
    }

}
