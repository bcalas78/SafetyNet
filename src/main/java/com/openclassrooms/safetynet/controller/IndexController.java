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

    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @Autowired
    private CommonService commonService;

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getChilAlertDTOByAddress(@RequestParam String address) {
        try {
            logger.info("Request GET: Get childAlert with success.");
            return commonService.getChildrenByAddress(address);
        } catch (Exception e) {
            logger.error("Request Get childAlert cannot succeed", e);
            return null;
        }
    }

    @GetMapping("/phoneAlert")
    public List<PhoneAlertDTO> getPhoneAlertsByFirestation(@RequestParam("firestation") String firestationNumber) {
        try {
            logger.info("Request GET: Get phoneAlert with success.");
            return commonService.getPhoneAlertByFirestation(firestationNumber);
        } catch (Exception e) {
            logger.error("Request Get phoneAlert cannot succeed", e);
            return null;
        }
    }

    @GetMapping("/fire")
    public List<FireDTO> getFireDTOsByAddress(@RequestParam String address) {
        try {
            logger.info("Request GET: Get fire with success.");
            return commonService.getFireDTOsByAddress(address);
        } catch (Exception e) {
            logger.error("Request Get fire cannot succeed", e);
            return null;
        }
    }

    @GetMapping("/flood/stations")
    public Map<String, List<FloodDTO>> getFloodStations(@RequestParam List<String> stations ) {
        try {
            logger.info("Request GET: Get flood/stations with success.");
            return commonService.getFloodStations(stations);
        } catch (Exception e) {
            logger.error("Request Get flood/stations cannot succeed", e);
            return null;
        }
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            logger.info("Request GET: Get personInfo with success.");
            return commonService.getPersonInfo(firstName, lastName);
        } catch (Exception e) {
            logger.error("Request Get personInfo cannot succeed", e);
            return null;
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam String city) throws Exception {
        try {
            logger.info("Request GET: Get communityEmail with success.");
            CommunityEmailDTO communityEmailDTO = commonService.getEmailsByCity(city);
            return ResponseEntity.ok(communityEmailDTO);
        } catch (Exception e) {
            logger.error("Request Get communityEmail cannot succeed", e);
            return null;
        }
    }
}
