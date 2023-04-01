package com.openclassrooms.safetynet.repository;

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

    @Override
    public void addPerson(Person person) {
        this.getPersons().add(person);
    }

    @Override
    public void deletePerson(String firstName, String lastName){
        List<Person> temporaryList = this.getPersons();
        for ( Person person: temporaryList ) {
            if(person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                this.getPersons().remove(person);
                break;
            }
        }
    }

    @Override
    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> temporaryList = this.getPersons();
        for (Person person : temporaryList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public Person saveAndUpdate(Person updatedPerson) {
        List<Person> temporaryList = this.getPersons();
        return updatedPerson;
    }

}
