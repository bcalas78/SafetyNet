package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {

    private static final Logger logger = LogManager.getLogger(FirestationService.class);

    @Autowired
    private FirestationRepository firestationRepository;

    public List<Firestation> getFirestations() throws Exception {
        try {
            logger.debug("Get firestations with success.");
            return firestationRepository.getFirestations();
        } catch (Exception e) {
            logger.error("Cannot get List of firestations", e);
            return null;
        }
    }

    public void addFirestation(Firestation firestation) throws Exception {
        try {
            logger.debug("Added firestation with success.");
            firestationRepository.addFirestation(firestation);
        } catch (Exception e) {
            logger.error("Cannot add firestation", e);
        }
    }

    public void deleteFirestation(String station, String address) throws Exception {
        try {
            logger.debug("Deleted firestation with success.");
            firestationRepository.deleteFirestation(station, address);
        } catch (Exception e) {
            logger.error("Cannot Delete firestation", e);
        }
    }

    public Firestation updateFirestation(Firestation updatedFirestation) throws Exception {
        try {
            logger.debug("Updated firestation with success.");
            Firestation firestationToUpdate = firestationRepository.findByAddress(updatedFirestation.getAddress());
            firestationToUpdate.setStation(updatedFirestation.getStation());
            return firestationRepository.saveAndUpdate(firestationToUpdate);
        } catch (Exception e) {
            logger.error("Cannot update firestation", e);
            return null;
        }
    }
}
