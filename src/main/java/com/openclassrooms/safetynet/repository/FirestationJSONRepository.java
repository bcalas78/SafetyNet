package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class FirestationJSONRepository extends FirestationRepository {

    @Override
    public void save(List<Firestation> firestationList) {

        this.setFirestations(firestationList);
    }

    @Override
    public void addFirestation(Firestation firestation) {
        this.getFirestations().add(firestation);
    }

    @Override
    public void deleteFirestation(String station, String address) {
        List<Firestation> firestationTemporaryList = this.getFirestations();
        for (Firestation firestation : firestationTemporaryList) {
            if (firestation.getStation().equals(station) && firestation.getAddress().equals(address)) {
                this.getFirestations().remove(firestation);
                break;
            }
        }
    }

    @Override
    public Firestation findByAddress(String address) {
        List<Firestation> firestationTemporaryList = this.getFirestations();
        for (Firestation firestation : firestationTemporaryList) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }
        return null;
    }

    @Override
    public Firestation saveAndUpdate(Firestation updatedFirestation) {
        List<Firestation> firestationTemporaryList = this.getFirestations();
        return updatedFirestation;
    }

    @Override
    public List<String> getAddressesByFirestationNumber(String firestationNumber) {
        List<String> addresses = new ArrayList<>();
        List<Firestation> temporaryFirestationList = this.getFirestations();
        for (Firestation firestation : temporaryFirestationList){
            if(firestation.getStation().equals(firestationNumber)) {
                addresses.add(firestation.getAddress());
            }
        }
        return addresses;
    }
}
