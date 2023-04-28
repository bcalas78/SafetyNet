package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.repository.FirestationRepository;
import com.openclassrooms.safetynet.service.FirestationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

    @InjectMocks
    private FirestationService firestationService;

    @Mock
    private FirestationRepository firestationRepository;

    @Test
    public void testGetFirestations() throws Exception {
        Firestation firestation1 = new Firestation();
        firestation1.setStation("1");
        firestation1.setAddress("1000 King Road");

        Firestation firestation2 = new Firestation();
        firestation2.setStation("2");
        firestation2.setAddress("1 Main St");

        List<Firestation> firestations = Arrays.asList(firestation1, firestation2);

        when(firestationRepository.getFirestations()).thenReturn(firestations);

        List<Firestation> actualFirestations = firestationService.getFirestations();

        Assert.assertEquals(firestations, actualFirestations);
        Assert.assertEquals(2, firestations.size());
        assertThat(firestations).contains(firestation1, firestation2);

        verify(firestationRepository, times(1)).getFirestations();
    }

    @Test
    public void testGetFirestationsCatch() throws Exception {
        Mockito.when(firestationRepository.getFirestations()).thenThrow(new RuntimeException("Mocked Exception"));

        List<Firestation> result = null;
        try {
            result = firestationService.getFirestations();
        } catch (Exception e) {
            Mockito.verify(firestationRepository).save(result);
            Mockito.verify(firestationService).getFirestations();
            Assert.assertEquals("Cannot get List of firestations", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddFirestation() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("1000 King Road");

        doNothing().when(firestationRepository).addFirestation(firestation);
        firestationService.addFirestation(firestation);
        verify(firestationRepository, times(1)).addFirestation(firestation);
    }

    @Test
    public void testAddFirestationCatch() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("1000 King Road");

        doThrow(new RuntimeException()).when(firestationRepository).addFirestation(firestation);
        firestationService.addFirestation(firestation);
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        String station = "2";
        String address = "123 Main St";

        firestationService.deleteFirestation(station, address);

        verify(firestationRepository, times(1)).deleteFirestation(station, address);
    }

    @Test
    public void testDeleteFirestationCatch() throws Exception {
        String station = "2";
        String address = "123 Main St";

        Mockito.doThrow(new RuntimeException("Cannot Delete firestation")).when(firestationRepository).deleteFirestation(station, address);

        try {
            firestationService.deleteFirestation(station, address);
        } catch (Exception e) {
            Assert.assertEquals("Cannot Delete firestation", e.getMessage());
        }
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        Firestation existingFirestation = new Firestation();
        existingFirestation.setStation("2");
        existingFirestation.setAddress("500 Main Road");

        when(firestationRepository.findByAddress("500 Main Road")).thenReturn(existingFirestation);

        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setStation("3");
        updatedFirestation.setAddress("500 Main Road");

        when(firestationRepository.saveAndUpdate(existingFirestation)).thenReturn(updatedFirestation);

        Firestation result = firestationService.updateFirestation(updatedFirestation);

        Assert.assertEquals(updatedFirestation, result);

        verify(firestationRepository).saveAndUpdate(existingFirestation);
    }

    @Test
    void testUpdateFirestationCatch() throws Exception {
        String station = "3";
        String address = "300 Test St";
        String errorMessage = "Cannot update firestation";

        when(firestationRepository.findByAddress(address)).thenThrow(new RuntimeException(errorMessage));

        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setAddress(address);

        Firestation result = firestationService.updateFirestation(updatedFirestation);

        assertNull(result);
    }
}
