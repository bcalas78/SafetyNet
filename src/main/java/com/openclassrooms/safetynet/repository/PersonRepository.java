package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Person;
import lombok.Data;

import java.util.List;

@Data
public abstract class PersonRepository {

    private List<Person> persons;
    public abstract void save(List<Person> personList);

    private Person person;
    public void addPerson(Person person) {
    }

    public abstract void deletePerson(String firstName, String lastName);

    public abstract Person saveAndUpdate(Person person);
    public abstract Person findByFirstNameAndLastName(String firstName, String lastName);
}
