package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Getter
@Repository
public class PersonJSONRepository extends PersonRepository {

    @Override
    public void save(List<Person> personList) {
        this.setPersons(personList);
    }
}
