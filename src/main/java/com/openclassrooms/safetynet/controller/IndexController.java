package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.service.CommonService;
import com.openclassrooms.safetynet.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private CommonService commonService;

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChilAlertDTOByAddress(@RequestParam String address) {
        try {
            logger.info("Request Get: Get childAlert with success.");
        } catch (Exception e) {
            logger.error("Request Get childAlert cannot succeed", e);
        }
        return commonService.getChildrenByAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<PhoneAlertDTO> getPhoneAlertsByFirestation(@RequestParam("firestation") String firestationNumber) {
        try {
            logger.info("Request Get: Get phoneAlert with success.");
        } catch (Exception e) {
            logger.error("Request Get phoneAlert cannot succeed", e);
        }
        return commonService.getPhoneAlertByFirestation(firestationNumber);
    }

    @GetMapping("/fire")
    public List<FireDTO> getFireDTOsByAddress(@RequestParam String address) {
        try {
            logger.info("Request Get: Get fire with success.");
        } catch (Exception e) {
            logger.error("Request Get fire cannot succeed", e);
        }
        return commonService.getFireDTOsByAddress(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<FloodDTO>> getFloodStations(@RequestParam List<String> stations ) {
        try {
            logger.info("Request Get: Get flood/stations with success.");
        } catch (Exception e) {
            logger.error("Request Get flood/stations cannot succeed", e);
        }
        return commonService.getFloodStations(stations);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            logger.info("Request Get: Get personInfo with success.");
        } catch (Exception e) {
            logger.error("Request Get personInfo cannot succeed", e);
        }
        return commonService.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam String city) throws IOException {
        CommunityEmailDTO communityEmailDTO = commonService.getEmailsByCity(city);
        try {
            logger.info("Request Get: Get communityEmail with success.");
        } catch (Exception e) {
            logger.error("Request Get communityEmail cannot succeed", e);
        }
        return ResponseEntity.ok(communityEmailDTO);
    }
}
