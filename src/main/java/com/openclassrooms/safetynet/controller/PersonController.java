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
            logger.info("Request Get: Get persons with success.");
        } catch (Exception e) {
            logger.error("Request Get Person cannot succeed", e);
        }
        return personService.getPersons();
    }

   @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) throws Exception {
        personService.addPerson(person);
       try {
           logger.info("Request Post: Person created with success.");
       } catch (Exception e) {
           logger.error("Cannot created person", e);
       }
        return new ResponseEntity<Person>(person,HttpStatus.CREATED);
   }

   @DeleteMapping
    public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) throws Exception {
        try {
            logger.info("Request Delete: deleted person with success.");
            personService.deletePerson(firstName, lastName);
        } catch (Exception e) {
            logger.error("Cannot delete person", e);
        }
   }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson) throws Exception{
        Person savedPerson = personService.updatePerson(updatedPerson);
        try {
            logger.info("Request Update: updated person with success.");
        } catch (Exception e) {
            logger.error("Cannot update person", e);
        }
        return ResponseEntity.ok(savedPerson);
    }

}
