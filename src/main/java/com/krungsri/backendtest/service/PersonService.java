package com.krungsri.backendtest.service;

import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.repository.PersonRepository;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(@Qualifier("postgres") PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Either<Integer, Integer> register(Person person) {
        int phoneLength = person.getPhoneNo().length();
        person.setRefCode(person.getPhoneNo().substring(phoneLength - 4, phoneLength));

        if (person.getSalary() < 0 || person.getSalary() < 15000) {
            return Either.left(0);
        } else {
            return personRepository.insertPerson(person);
        }
    }

    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

}
