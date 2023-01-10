package com.ftn.clinicCentre.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "nurse")
public class Nurse extends User {

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    public Nurse() {}

    public Nurse(String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, EUserStatus status, Clinic clinic) {
        super(firstName, lastName, jmbg, address, email, password, gender, authorities, status);
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
        return "Nurse{" +
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
                ", clinic=" + clinic +
                '}';
    }
}
