package com.ftn.clinicCentre.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinic_administrator")
public class ClinicAdministrator extends User {

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public ClinicAdministrator() {}

    public ClinicAdministrator(String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, EUserStatus status) {
        super(firstName, lastName, jmbg, address, email, password, gender, authorities, status);
    }

    public ClinicAdministrator(String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, EUserStatus status, Clinic clinic) {
        super(firstName, lastName, jmbg, address, email, password, gender, authorities, status);
        this.clinic = clinic;
    }

    public ClinicAdministrator(Long id, String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, EUserStatus status, Clinic clinic) {
        super(id, firstName, lastName, jmbg, address, email, password, gender, authorities, status);
        this.clinic = clinic;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public String toString() {
        return "ClinicAdministrator{" +
                " id='" + super.getId() + '\'' +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", jmbg='" + super.getJmbg() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", email='" + super.getEmail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", gender=" + super.getGender() +
                ", authorities=" + super.getAuthorities() +
                ", userStatus=" + super.getStatus() +
                ", clinic=" + clinic +
                '}';
    }
}