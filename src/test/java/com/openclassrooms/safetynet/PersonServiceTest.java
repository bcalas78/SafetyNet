package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.PersonService;
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
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Test
    public void testGetPersons() throws Exception {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setAddress("1000 King Road");
        person1.setCity("London");
        person1.setZip("1000");
        person1.setPhone("555-555-5555");
        person1.setEmail("johndoe@email.com");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("Doe");
        person2.setAddress("1000 King Road");
        person2.setCity("London");
        person2.setZip("1000");
        person2.setPhone("444-555-5555");
        person2.setEmail("janedoe@email.com");

        List<Person> persons = Arrays.asList(person1, person2);

        when(personRepository.getPersons()).thenReturn(persons);

        List<Person> actualPersons = personService.getPersons();

        assertEquals(persons, actualPersons);
        assertEquals(2, persons.size());
        assertThat(persons).contains(person1, person2);

        verify(personRepository, times(1)).getPersons();
    }

    @Test
    public void testGetPersonsCatch() throws Exception {
        Mockito.when(personRepository.getPersons()).thenThrow(new RuntimeException("Mocked Exception"));

        List<Person> result = null;
        try {
            result = personService.getPersons();
        } catch (Exception e) {
            Mockito.verify(personRepository).save(result);
            Mockito.verify(personService).getPersons();
            Assert.assertEquals("Cannot get List of persons", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("1000 King Road");
        person.setCity("London");
        person.setZip("1000");
        person.setPhone("555-555-5555");
        person.setEmail("johndoe@email.com");

        doNothing().when(personRepository).addPerson(person);
        personService.addPerson(person);
        verify(personRepository, times(1)).addPerson(person);
    }

    @Test
    public void testAddPersonCatch() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("1000 King Road");
        person.setCity("London");
        person.setZip("1000");
        person.setPhone("555-555-5555");
        person.setEmail("johndoe@email.com");

        doThrow(new RuntimeException()).when(personRepository).addPerson(person);
        personService.addPerson(person);
    }

    @Test
    public void testDeletePerson() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        personService.deletePerson(firstName, lastName);

        verify(personRepository, times(1)).deletePerson(firstName, lastName);
    }

    @Test
    public void testDeletePersonCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";

        Mockito.doThrow(new RuntimeException("Cannot Delete person")).when(personRepository).deletePerson(firstName, lastName);

        try {
            personService.deletePerson(firstName, lastName);
        } catch (Exception e) {
            assertEquals("Cannot Delete person", e.getMessage());
        }
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person existingPerson = new Person();
        existingPerson.setFirstName("Janette");
        existingPerson.setLastName("Doe");
        existingPerson.setEmail("janettedoe@email.com");

        when(personRepository.findByFirstNameAndLastName("Janette", "Doe")).thenReturn(existingPerson);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Janette");
        updatedPerson.setLastName("Doe");
        updatedPerson.setEmail("janette.doe123@email.com");

        when(personRepository.saveAndUpdate(existingPerson)).thenReturn(updatedPerson);

        Person result = personService.updatePerson(updatedPerson);

        assertEquals(updatedPerson, result);

        verify(personRepository).saveAndUpdate(existingPerson);
    }

    @Test
    void testUpdatePersonCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        String errorMessage = "Cannot update person";

        when(personRepository.findByFirstNameAndLastName(firstName, lastName)).thenThrow(new RuntimeException(errorMessage));

        Person updatedPerson = new Person();
        updatedPerson.setFirstName(firstName);
        updatedPerson.setLastName(lastName);

        Person result = personService.updatePerson(updatedPerson);

        assertNull(result);
    }
}
