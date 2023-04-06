package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Firestation;
import lombok.Data;

import java.util.List;


@Data
public abstract class FirestationRepository {

    private List<Firestation> firestations;
    public abstract void save(List<Firestation> firestationList);

    private Firestation firestation;
    public void addFirestation(Firestation firestation) {}

    public abstract void deleteFirestation(String station, String address);

    public abstract Firestation saveAndUpdate(Firestation firestation);

    public abstract Firestation findByAddress(String address);

    public abstract List<String> getAddressesByFirestationNumber(String firestationNumber);
}
