package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Person;
import lombok.Data;

import java.util.List;

@Data
public abstract class PersonRepository {

    private List<Person> persons;
    public abstract void save(List<Person> personList);
}
