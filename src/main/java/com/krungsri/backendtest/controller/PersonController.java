package com.krungsri.backendtest.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krungsri.backendtest.dto.PersonDTO;
import com.krungsri.backendtest.exception.InvalidSalaryException;
import com.krungsri.backendtest.helper.ResponseHandler;
import com.krungsri.backendtest.model.ApiToken;
import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;
import static io.vavr.Predicates.instanceOf;

@RestController
public class PersonController {

    private final PersonService personService;
    private final ResponseHandler resHandler;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    public PersonController(PersonService personService, ResponseHandler resHandler) {
        this.personService = personService;
        this.resHandler = resHandler;
    }

    @RequestMapping(value = "api/v1/person", method = RequestMethod.POST)
    public ObjectNode registerPerson(@Valid @NonNull @RequestBody Person person) {
        ObjectNode response = Match(personService.register(person)).of(
                Case($Right($()), v ->
                        resHandler.successResponse(new ApiToken(Jwts.builder()
                                        .claim("roles", "user")
                                        .setSubject(person.getUsername())
                                        .setIssuedAt(new Date())
                                        .signWith(SignatureAlgorithm.HS256, secret)
                                        .compact())
                                , 200)),
                Case($Left($(instanceOf(InvalidSalaryException.class))), e ->
                        resHandler.failureResponse(e.getMessage(), 400)),
                Case($Left($(instanceOf(DuplicateKeyException.class))), e ->
                        resHandler.failureResponse(e.getMessage(), 409)),
                Case($Left($(instanceOf(Exception.class))), e ->
                        resHandler.failureResponse(e.getMessage(), 500))
        );

        return response;
    }

    @RequestMapping(value = "api/v1/secured/person", method = RequestMethod.GET)
    public ObjectNode getAllPerson() {

        return resHandler.successResponse(personService.getAllPerson(), 200);
    }

    @RequestMapping(value = "api/v1/secured/person/{id}", method = RequestMethod.GET)
    public ObjectNode getUserById(@PathVariable("id") UUID id) {
        Optional<PersonDTO> personOpt = personService.getPersonById(id);

        if (personOpt.isPresent())
            return resHandler.successResponse(personOpt.get(), 200);
        else
            return resHandler.failureResponse("Person not found", 404);
    }
}
