package com.krungsri.backendtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Person {

    private final UUID id;
    @NotBlank
    private final String username;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNo;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String address;

    private long salary;

    private String refCode;

    public Person(UUID id,
                  @JsonProperty(value = "username", required = true) String username,
                  @JsonProperty(value = "password", required = true) String password,
                  @JsonProperty(value = "phoneNo", required = true) String phoneNo,
                  @JsonProperty(value = "firstName", required = true) String firstName,
                  @JsonProperty(value = "lastName", required = true) String lastName,
                  @JsonProperty(value = "address", required = true) String address,
                  @JsonProperty(value = "salary", required = true) long salary,
                  String refCode) {
        this.id = id;
        this.refCode = refCode;
        this.username = username;
        this.password = password;
        this.phoneNo = phoneNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.salary = salary;
    }

    public UUID getId() { return id; }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRefCode() { return refCode; }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public long getSalary() {
        return salary;
    }

}
