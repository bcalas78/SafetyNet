package com.openclassrooms.safetynet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PersonController personController;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPersonListCatch() throws Exception {
        Mockito.when(personService.getPersons()).thenThrow(new Exception("Mocked Exception"));

        List<Person> result = null;
        try {
            result = personController.personList();
        } catch (Exception e) {
            Mockito.verify(personService).getPersons();
            Mockito.verify(personController).personList();
            Assert.assertEquals("Request Get personList cannot succeed", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testAddPerson() throws Exception {
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPerson = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.firstName", is("John")))
                        .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testAddPersonCatch() throws Exception {
        Person person = new Person();
        person.setFirstName("Ruben");
        person.setLastName("Aguilar");

        Mockito.doThrow(new Exception("Mocked Exception")).when(personService).addPerson(person);

        ResponseEntity<Person> result = null;
        try {
            result = personController.addPerson(person);
        } catch (Exception e) {
            Mockito.verify(personService).addPerson(person);
            Mockito.verify(personController).addPerson(person);
            Assert.assertEquals("Cannot created person", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person personToUpdate = new Person();
        personToUpdate.setFirstName("John");
        personToUpdate.setLastName("Doe");
        personToUpdate.setAddress("1000 King Road");
        personToUpdate.setCity("London");
        personToUpdate.setZip("1000");
        personToUpdate.setPhone("111-111-1111");
        personToUpdate.setEmail("johndoe@email.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPerson = objectMapper.writeValueAsString(personToUpdate);

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPerson))
                        .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePersonCatch() throws Exception {
        Person updatedPerson = new Person();
        updatedPerson.setFirstName("John");
        updatedPerson.setLastName("Doe");
        Mockito.when(personService.updatePerson(updatedPerson)).thenThrow(new Exception("Mocked Exception"));

        ResponseEntity<Person> result = null;
        try {
            result = personController.updatePerson(updatedPerson);
        } catch (Exception e) {
            Mockito.verify(personService).updatePerson(updatedPerson);
            Mockito.verify(personController).updatePerson(updatedPerson);
            Assert.assertEquals("Cannot update person", e.getMessage());
        }

        Assert.assertNull("Result should be null", result);
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person personToDelete = new Person();
        personToDelete.setFirstName("John");
        personToDelete.setLastName("Doe");
        personToDelete.setAddress("1000 King Road");
        personToDelete.setCity("London");
        personToDelete.setZip("1000");
        personToDelete.setPhone("111-111-1111");
        personToDelete.setEmail("johndoe@email.com");

        personService.addPerson(personToDelete);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    public void testDeletePersonCatch() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        Mockito.doThrow(new Exception("Mocked Exception")).when(personService).deletePerson(firstName, lastName);

        try {
            personController.deletePerson(firstName, lastName);
        } catch (Exception e) {
            // Assert
            Mockito.verify(personService).deletePerson(firstName, lastName);
            Mockito.verify(personController).deletePerson(firstName, lastName);
            Assert.assertEquals("Cannot delete person", e.getMessage());
        }
    }
}
