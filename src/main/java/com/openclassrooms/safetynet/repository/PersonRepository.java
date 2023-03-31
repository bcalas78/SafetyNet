package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Person;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public abstract class PersonRepository {

    private List<Person> persons;
    public abstract void save(List<Person> personList);

    private Person person;
    public void addPerson(Person person) {
    }

    public abstract void deletePerson(String firstName, String lastName);
    //public abstract void updatePerson(Person person);

}
