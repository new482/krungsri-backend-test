package com.krungsri.backendtest.controller;

import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
        personService.register(person);
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
