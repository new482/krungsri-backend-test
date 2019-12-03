package com.krungsri.backendtest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Person {

    private final UUID id;
    private final String username;
    private String password;
    private String phoneNo;
    private String firstName;
    private String lastName;
    private String address;
    private long salary;
    private String refCode;

    public Person(UUID id,
                  @JsonProperty("username") String username,
                  @JsonProperty("password") String password,
                  @JsonProperty("phoneNo") String phoneNo,
                  @JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName,
                  @JsonProperty("address") String address,
                  @JsonProperty("salary") long salary,
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
