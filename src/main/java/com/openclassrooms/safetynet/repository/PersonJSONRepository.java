package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Firestation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
public class PersonJSONRepository extends PersonRepository {

    @Autowired
    private FirestationRepository firestationRepository;

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

    @Override
    public List<Person> getPersonsByAddress(String address) {
        List<Person> personsAtAddress = new ArrayList<>();
        List<Person> temporaryPersonList = this.getPersons();
        for (Person person : temporaryPersonList) {
            if (person.getAddress().equals(address)) {
                personsAtAddress.add(person);
            }
        }
        return personsAtAddress;
    }

    @Override
    public List<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> personByFirstNameAndLastName = new ArrayList<>();
        List<Person> temporaryPersonList = this.getPersons();
        for (Person person : temporaryPersonList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)){
                personByFirstNameAndLastName.add(person);
            }
        }
        return personByFirstNameAndLastName;
    }

    @Override
    public List<Person> getPersonByFirestationNumber(String stationNumber) {
        List<Person> personByFirestationNumber = new ArrayList<>();
        List<Firestation> firestations = firestationRepository.getFirestationByStationNumber(stationNumber);

        for (Firestation firestation : firestations) {
            String address = firestation.getAddress();
            List<Person> personsByAdress = getPersonsByAddress(address);
            personByFirestationNumber.addAll(personsByAdress);
        }
        return personByFirestationNumber;
    }


}
