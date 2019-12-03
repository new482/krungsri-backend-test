package com.krungsri.backendtest.service;

import com.krungsri.backendtest.dto.PersonDTO;
import com.krungsri.backendtest.model.Person;
import com.krungsri.backendtest.repository.PersonRepository;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(@Qualifier("postgres") PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Either<Exception, Integer> register(Person person) {
        if (person.getSalary() >= 15000) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String formattedDate = formatter.format(date);
            int phoneLength = person.getPhoneNo().length();
            String formattedPhoneNo = person.getPhoneNo().substring(phoneLength - 4, phoneLength);

            person.setRefCode(formattedDate + formattedPhoneNo);

            return personRepository.insertPerson(person);
        } else {
            return Either.left(new Exception(""));
        }
    }

    public List<PersonDTO> getAllPerson() {
        List<Person> personList = personRepository.findAll();
        return personList
                .stream()
                .map(v -> new PersonDTO(
                        v.getId(),
                        v.getPhoneNo(),
                        v.getFirstName(),
                        v.getLastName(),
                        v.getAddress(),
                        v.getSalary(),
                        v.getRefCode(),
                        classifyMemberType(v.getSalary()))
        ).collect(Collectors.toList());
    }

    public Optional<PersonDTO> getPersonById(UUID id) {

        return personRepository.findById(id)
                .map(v -> new PersonDTO(
                        v.getId(),
                        v.getPhoneNo(),
                        v.getFirstName(),
                        v.getLastName(),
                        v.getAddress(),
                        v.getSalary(),
                        v.getRefCode(),
                        classifyMemberType(v.getSalary()))

                );
    }

    private String classifyMemberType(Long salary) {
        if (salary > 50000)
            return "Platinum";
        else if (salary > 30000)
            return "Gold";
        else
            return "Silver";
    }

}
