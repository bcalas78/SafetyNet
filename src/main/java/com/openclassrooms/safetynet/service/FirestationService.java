package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;

    public List<Firestation> getFirestations() {
        return firestationRepository.getFirestations();
    }

    public void addFirestation(Firestation firestation) {
        firestationRepository.addFirestation(firestation);
    }

    public void deleteFirestation(String station, String address) {
        firestationRepository.deleteFirestation(station, address);
    }

    public Firestation updateFirestation(Firestation updatedFirestation) {
        Firestation firestationToUpdate = firestationRepository.findByAddress(updatedFirestation.getAddress());
        firestationToUpdate.setStation(updatedFirestation.getStation());

        return firestationRepository.saveAndUpdate(firestationToUpdate);
    }
}
