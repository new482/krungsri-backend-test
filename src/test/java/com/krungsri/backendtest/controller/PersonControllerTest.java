package com.krungsri.backendtest.controller;

import com.krungsri.backendtest.config.JWTConfig;
import com.krungsri.backendtest.dto.PersonDTO;
import com.krungsri.backendtest.exception.InvalidSalaryException;
import com.krungsri.backendtest.helper.JWTFilter;
import com.krungsri.backendtest.helper.ResponseHandler;
import com.krungsri.backendtest.model.ApiToken;
import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.service.PersonService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private JWTConfig jwtConfig;

    @MockBean
    private ResponseHandler resHandler;

    private Person mockPerson = new Person(UUID.randomUUID(),
            "username",
            "password",
            "123456789",
            "firstname",
            "lastname",
            "address",
            40000,
            "ref12345");

    private PersonDTO mockPersonDTO = new PersonDTO(UUID.randomUUID(),
            mockPerson.getPhoneNo(),
            mockPerson.getFirstName(),
            mockPerson.getLastName(),
            mockPerson.getAddress(),
            mockPerson.getSalary(),
            mockPerson.getRefCode(),
            "Gold");

    private ApiToken apiToken = new ApiToken(Jwts.builder()
            .claim("roles", "user")
            .setSubject(mockPerson.getUsername())
            .setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "YC29#&*NEW492")
            .compact());

    @Test
    void registerPerson() throws Exception {
        String mockJson = "{\n" +
                "\"username\": \"testname\"" +
                "\"password\": \"testpassword\"" +
                "\"phoneNo\": \"testphoneNo\"" +
                "\"firstName\": \"testname\"" +
                "\"lastName\": \"testname\"" +
                "\"address\": \"address\"" +
                "\"salary\": " + 40000 + "" +
                "}";

        when(personService.register(mockPerson)).thenReturn(Either.right("success"));

        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockJson);

        mvc.perform(reqBuilder).andExpect(status().isOk());
    }

    @Test
    void registerPersonInvalidSalary() throws Exception {
        String mockJson = "{\n" +
                "\"username\": \"testname\"" +
                "\"password\": \"testpassword\"" +
                "\"phoneNo\": \"testphoneNo\"" +
                "\"firstName\": \"testname\"" +
                "\"lastName\": \"testname\"" +
                "\"address\": \"address\"" +
                "\"salary\": " + 4000 + "" +
                "}";

        when(personService.register(mockPerson)).thenReturn(Either.left(new InvalidSalaryException("")));

        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/api/v1/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockJson);

        mvc.perform(reqBuilder).andExpect(status().isBadRequest());
    }

    @Test
    void getAllPerson() throws Exception {
        List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(mockPersonDTO);
        when(personService.getAllPerson()).thenReturn(personDTOList);

        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .get("/api/v1/secured/person")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(reqBuilder).andExpect(status().isOk());
    }
//
//    @Test
//    void getUserById() {
//    }
}