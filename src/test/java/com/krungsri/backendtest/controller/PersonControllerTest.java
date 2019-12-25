package com.krungsri.backendtest.controller;

import com.krungsri.backendtest.helper.ResponseHandler;
import com.krungsri.backendtest.model.ApiToken;
import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private ResponseHandler resHandler;

    private Person person = new Person(UUID.randomUUID(),
            "username",
            "password",
            "123456789",
            "firstname",
            "lastname",
            "address",
            40000,
            "ref12345");

    @Test
    void registerPerson() {
        ApiToken apiToken = new ApiToken(Jwts.builder()
                .claim("roles", "user")
                .setSubject(person.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact());
        Mockito.when(personService.register(person)).thenReturn(Either.right("success"));
    }

    @Test
    void getAllPerson() {
    }

    @Test
    void getUserById() {
    }
}