package com.krungsri.backendtest.repository;

import com.krungsri.backendtest.model.Person;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonRepositoryImp implements PersonRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public PersonRepositoryImp(JdbcTemplate jdbc) {

        this.jdbc = jdbc;
    }

    @Override
    public Either<Exception, Integer> insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person (id, username, password, phone_no, " +
                "first_name, last_name, address, salary, ref_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbc.update(
                    sql,
                    id,
                    person.getUsername(),
                    person.getPassword(),
                    person.getPhoneNo(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getAddress(),
                    person.getSalary(),
                    person.getRefCode()
            );
            System.out.println("Insert success");
            return Either.right(1);
        } catch (Exception e) {
            System.out.println("Insert error: " + e);
            return Either.left(e);
        }
    }

    @Override
    public List<Person> findAll() {
        final String sql = "SELECT * FROM person";
        List<Person> people = jdbc.query(sql, (rs, i) -> {
            return new Person(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("phone_no"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getLong("salary"),
                    rs.getString("ref_code")
            );
        });
        return people;
    }

    @Override
    public Optional<Person> findById(UUID id) {
        final String sql = "SELECT * FROM person WHERE id = ?";

        Person person = jdbc.queryForObject(sql, new Object[]{id}, (rs, i) -> {
            return new Person(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("phone_no"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address"),
                    rs.getLong("salary"),
                    rs.getString("ref_code")
            );
        });

        return Optional.ofNullable(person);
    }
}
