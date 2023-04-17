package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    public void testGetPersons() {
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

        personService.addPerson(person1);
        personService.addPerson(person2);

        List<Person> persons = personService.getPersons();

        assertThat(persons).contains(person1, person2);
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person personToDelete = new Person();
        personToDelete.setFirstName("Johnathan");
        personToDelete.setLastName("Doe");
        personService.addPerson(personToDelete);

        personService.deletePerson("Johnathan", "Doe");
        Person deletedPerson = personRepository.findByFirstNameAndLastName("Johnathan","Doe");
        assertNull(deletedPerson);
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person personToUpdate = new Person();
        personToUpdate.setFirstName("Janette");
        personToUpdate.setLastName("Doe");
        personToUpdate.setEmail("janettedoe@email.com");
        personService.addPerson(personToUpdate);

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Janette");
        updatedPerson.setLastName("Doe");
        updatedPerson.setEmail("janette.doe123@email.com");

        personService.updatePerson(updatedPerson);

        Person retrievedPerson = personRepository.findByFirstNameAndLastName("Janette", "Doe");
        assertEquals(updatedPerson.getEmail(), retrievedPerson.getEmail());
    }
}