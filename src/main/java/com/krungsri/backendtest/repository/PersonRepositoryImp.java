package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.Person;
import io.vavr.control.Either;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonRepositoryImp implements PersonRepository {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public Either<Integer, Integer> insertPerson(UUID id, Person person) {
        DB.add(new Person(id,
                person.getUsername(),
                person.getPassword(),
                person.getPhoneNo(),
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getSalary(),
                person.getRefCode()));

        return Either.right(1);
    }

    @Override
    public List<Person> findAll() {
        return DB;
    }

    @Override
    public Optional<Person> findById(UUID id) {
        return DB.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }
}
