package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersons() {
        return personRepository.getPersons();
    }

    public void addPerson(Person person) {
        personRepository.addPerson(person);
    }

    public void deletePerson(String firstName, String lastName){
        personRepository.deletePerson(firstName, lastName);
    }

    public Person updatePerson(Person updatedPerson) {
        Person personToUpdate = personRepository.findByFirstNameAndLastName(updatedPerson.getFirstName(), updatedPerson.getLastName());
        personToUpdate.setAddress(updatedPerson.getAddress());
        personToUpdate.setCity(updatedPerson.getCity());
        personToUpdate.setZip(updatedPerson.getZip());
        personToUpdate.setPhone(updatedPerson.getPhone());
        personToUpdate.setEmail(updatedPerson.getEmail());

        return personRepository.saveAndUpdate(personToUpdate);
    }

}
