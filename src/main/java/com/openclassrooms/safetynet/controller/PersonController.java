package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> personList() {
        return personService.getPersons();
    }

   @PostMapping
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        personService.addPerson(person);
        return new ResponseEntity<Person>(person,HttpStatus.CREATED);
   }

   @DeleteMapping
    public void deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        personService.deletePerson(firstName, lastName);
   }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody Person updatedPerson) {
        Person savedPerson = personService.updatePerson(updatedPerson);
        return ResponseEntity.ok(savedPerson);
    }

}
