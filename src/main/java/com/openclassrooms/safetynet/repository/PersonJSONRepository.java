package com.openclassrooms.safetynet.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.Person;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Getter
@Repository
public class PersonJSONRepository extends PersonRepository {

    @Override
    public void save(List<Person> personList) {
        this.setPersons(personList);
    }

    @Override
    public void addPerson(Person person) {
        this.getPersons().add(person);
    }

    @Override
    public void deletePerson(String firstName, String lastName){
        this.deletePerson(firstName, lastName);
    }


}
