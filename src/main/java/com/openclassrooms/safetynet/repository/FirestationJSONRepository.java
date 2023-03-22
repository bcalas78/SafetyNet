package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Firestation;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class FirestationJSONRepository extends FirestationRepository {


    @Override
    public void save(List<Firestation> firestationList) {
        this.setFirestations(firestationList);
    }
}
