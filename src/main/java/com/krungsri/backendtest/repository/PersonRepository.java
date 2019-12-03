package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.Person;
import io.vavr.control.Either;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {
    Either<Exception, Integer> insertPerson(UUID id, Person person);

    default Either<Exception, Integer> insertPerson(Person person) {

        return insertPerson(UUID.randomUUID(), person);
    }

    List<Person> findAll();

    Optional<Person> findById(UUID id);
}
