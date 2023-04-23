package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private static final Logger logger = LogManager.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersons() throws Exception {
        try {
            logger.debug("Get persons with success.");
        } catch (Exception e) {
            logger.error("Cannot get List of persons", e);
        }
        return personRepository.getPersons();
    }

    public void addPerson(Person person) throws Exception {
        try {
            logger.debug("Added person with success.");
            personRepository.addPerson(person);
        } catch (Exception e) {
            logger.error("Cannot add person", e);
        }
    }

    public void deletePerson(String firstName, String lastName) throws Exception {
        try {
            logger.debug("Deleted person with success.");
            personRepository.deletePerson(firstName, lastName);
        } catch (Exception e) {
            logger.error("Cannot Delete person", e);
        }
    }

    public Person updatePerson(Person updatedPerson) throws Exception {
        Person personToUpdate = personRepository.findByFirstNameAndLastName(updatedPerson.getFirstName(), updatedPerson.getLastName());
        personToUpdate.setAddress(updatedPerson.getAddress());
        personToUpdate.setCity(updatedPerson.getCity());
        personToUpdate.setZip(updatedPerson.getZip());
        personToUpdate.setPhone(updatedPerson.getPhone());
        personToUpdate.setEmail(updatedPerson.getEmail());
        try {
            logger.debug("Updated person with success.");
        } catch (Exception e) {
            logger.error("Cannot update person", e);
        }
        return personRepository.saveAndUpdate(personToUpdate);
    }

}
