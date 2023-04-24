package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> personList() throws Exception {
        try {
            logger.info("Request GET: Get personList with success.");
            return personService.getPersons();
        } catch (Exception e) {
            logger.error("Request Get personList cannot succeed", e);
            return null;
        }
    }

   @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws Exception {
       try {
           logger.info("Request POST: person created with success.");
           personService.addPerson(person);
           return new ResponseEntity<Person>(person,HttpStatus.CREATED);
       } catch (Exception e) {
           logger.error("Cannot created person", e);
           return null;
       }
   }

   @DeleteMapping
    public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws Exception {
        try {
            logger.info("Request DELETE: deleted person with success.");
            personService.deletePerson(firstName, lastName);
        } catch (Exception e) {
            logger.error("Cannot delete person", e);
        }
   }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson) throws Exception{
        try {
            logger.info("Request PUT: updated person with success.");
            Person savedPerson = personService.updatePerson(updatedPerson);
            return ResponseEntity.ok(savedPerson);
        } catch (Exception e) {
            logger.error("Cannot update person", e);
            return null;
        }
    }
}
