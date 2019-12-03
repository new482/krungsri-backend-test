package com.krungsri.backendtest.dto;

import java.util.UUID;

public class PersonDTO {
    private final UUID id;
    private String phoneNo;
    private String firstName;
    private String lastName;
    private String address;
    private long salary;
    private String refCode;
    private String memberType;

    public PersonDTO(UUID id,
                     String phoneNo,
                     String firstName,
                     String lastName,
                     String address,
                     long salary,
                     String refCode,
                     String memberType) {
        this.id = id;
        this.phoneNo = phoneNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.salary = salary;
        this.refCode = refCode;
        this.memberType = memberType;
    }

    public UUID getId() {
        return id;
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

    public String getRefCode() {
        return refCode;
    }

    public String getMemberType() {
        return memberType;
    }
}
