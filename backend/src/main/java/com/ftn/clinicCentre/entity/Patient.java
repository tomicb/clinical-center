package com.ftn.clinicCentre.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient extends User {

    public Patient() {}

    public Patient(String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, EUserStatus status) {
        super(firstName, lastName, jmbg, address, email, password, gender, authorities, status);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + super.getId() + '\'' +
                "firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", jmbg='" + super.getJmbg() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", gender=" + super.getGender() +
                ", authorities=" + super.getAuthorities() +
                ", userStatus=" + super.getStatus() +
                '}';
    }
}
