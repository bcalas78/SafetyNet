package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.service.DatabaseManipulation;
import com.openclassrooms.safetynet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DatabaseManipulation databaseManipulation;

    @MockBean
    private PersonService personService;

    @Test
    public void testGetPersons() throws Exception {
        mockMvc.perform(get("/person"))
                .andExpect(status().isOk());
    }
}
