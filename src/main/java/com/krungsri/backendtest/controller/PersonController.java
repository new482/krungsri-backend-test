package com.krungsri.backendtest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krungsri.backendtest.dto.PersonDTO;
import com.krungsri.backendtest.exception.InvalidSalaryException;
import com.krungsri.backendtest.helper.ResponseHandler;
import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static io.vavr.Predicates.instanceOf;

@RequestMapping("api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;
    private final ResponseHandler resHandler;

    @Autowired
    public PersonController(PersonService personService, ResponseHandler resHandler) {
        this.personService = personService;
        this.resHandler = resHandler;
    }

    @PostMapping
    public ObjectNode registerPerson(@Valid @NonNull @RequestBody Person person) {
        ObjectNode response = Match(personService.register(person)).of(
                Case($Right($()), v -> resHandler.successResponse(v, 200)),
                Case($Left($(instanceOf(InvalidSalaryException.class))), e -> resHandler.failureResponse(e.getMessage(), 400)),
                Case($Left($(instanceOf(Exception.class))), e -> resHandler.failureResponse("test", 400))
        );

        return response;
    }

    @GetMapping
    public ObjectNode getAllPerson() {

        return resHandler.successResponse(personService.getAllPerson(), 200);
    }

    @GetMapping(path = "{id}")
    public ObjectNode getUserById(@PathVariable("id") UUID id) {
        Optional<PersonDTO> personOpt = personService.getPersonById(id);

        if (personOpt.isPresent())
            return resHandler.successResponse(personOpt.get(), 200);
        else
            return resHandler.failureResponse("Person not found", 404);
    }
}
