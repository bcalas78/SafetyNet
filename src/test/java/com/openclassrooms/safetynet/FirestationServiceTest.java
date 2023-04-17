package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @Autowired
    private FirestationService firestationService;

    @Autowired
    private FirestationRepository firestationRepository;

    @Test
    public void testGetFirestations() throws Exception {
        Firestation firestation1 = new Firestation();
        firestation1.setStation("1");
        firestation1.setAddress("1000 King Road");

        Firestation firestation2 = new Firestation();
        firestation2.setStation("2");
        firestation2.setAddress("1 Main St");

        firestationService.addFirestation(firestation1);
        firestationService.addFirestation(firestation2);

        List<Firestation> firestations = firestationService.getFirestations();

        assertThat(firestations).contains(firestation1, firestation2);
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        Firestation firestationToDelete = new Firestation();
        firestationToDelete.setStation("1");
        firestationToDelete.setAddress("2500 Queen St");
        firestationService.addFirestation(firestationToDelete);

        firestationService.deleteFirestation("1", "2500 Queen St");
        Firestation deletedFirestation = firestationRepository.findByAddress("2500 Queen St");

        assertNull(deletedFirestation);
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setStation("2");
        firestationToUpdate.setAddress("500 Main Road");
        firestationService.addFirestation(firestationToUpdate);

        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setStation("3");
        updatedFirestation.setAddress("500 Main Road");

        firestationService.updateFirestation(updatedFirestation);

        Firestation retrievedFirestation = firestationRepository.findByAddress("500 Main Road");
        assertEquals(updatedFirestation.getStation(), retrievedFirestation.getStation());
    }
}
