package com.krungsri.backendtest.controller;

import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static io.vavr.Predicates.instanceOf;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public void registerPerson(@Valid @NonNull @RequestBody Person person) {
        Match(personService.register(person)).of(
                Case($Right($()), v -> v),
                Case($Left($(instanceOf(Exception.class))), e -> e)
        );
    }

    @GetMapping
    public List<Person> getAllPerson() {
        return personService.getAllPerson();
    }

    @GetMapping(path = "{id}")
    public Person getUserById(@PathVariable("id") UUID id) {
        return personService.getPersonById(id).orElse(null);
    }
}
