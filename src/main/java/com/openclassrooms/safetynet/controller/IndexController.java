package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.CommunityEmailDTO;
import com.openclassrooms.safetynet.model.PhoneAlertDTO;
import com.openclassrooms.safetynet.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private CommonService commonService;

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam String city) throws IOException {
        CommunityEmailDTO communityEmailDTO = commonService.getEmailsByCity(city);
        return ResponseEntity.ok(communityEmailDTO);
    }

    @GetMapping("/phoneAlert")
    public List<PhoneAlertDTO> getPhoneAlertsByFirestation(@RequestParam("firestationNumber") String firestationNumber) {
        return commonService.getPhoneAlertByFirestation(firestationNumber);
    }
}
