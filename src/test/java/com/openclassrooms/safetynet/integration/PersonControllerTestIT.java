package com.openclassrooms.safetynet.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("John")));
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
}
