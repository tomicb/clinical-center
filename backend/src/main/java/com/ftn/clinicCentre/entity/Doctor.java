package com.ftn.clinicCentre.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor extends User {

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @Column(name = "rating")
    private Double rating;

    public Doctor() {}

    public Doctor(String firstName, String lastName, String jmbg, String address, String email, String password, EGender gender, List<Authority> authorities, Double rating, EUserStatus status, Clinic clinic) {
        super(firstName, lastName, jmbg, address, email, password, gender, authorities, status);
        this.rating = rating;
        this.clinic = clinic;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Doctor{" +
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
                ", rating=" + rating +
                '}';
    }
}
