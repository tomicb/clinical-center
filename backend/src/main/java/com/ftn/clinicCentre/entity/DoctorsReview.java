package com.ftn.clinicCentre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "doctors_review")
public class DoctorsReview {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	
	@OneToOne
	private Doctor doctor;
	
	@OneToOne
	private Patient patient;
	
	@Column(name = "rating", nullable = false, unique = false)
	private double rating;
	
	public DoctorsReview() {}

	public DoctorsReview(Long id, Doctor doctor, Patient patient, double rating) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.patient = patient;
		this.rating = rating;
	}
	
	public DoctorsReview( Doctor doctor, Patient patient, double rating) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "DoctorsReview [id=" + id + ", doctor=" + doctor + ", patient=" + patient + ", rating=" + rating + "]";
	}
	
	
	

}
