package com.ftn.clinicCentre.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clinics_review")
public class ClinicReview {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
	
	@OneToOne
	private Clinic clinic;
	
	@OneToOne
	private Patient patient;
	
	@Column(name = "rating", nullable = false, unique = false)
	private double rating;
	
	public ClinicReview() {}

	public ClinicReview(Clinic clinic, Patient patient, double rating) {
		super();
		this.clinic = clinic;
		this.patient = patient;
		this.rating = rating;
	}

	public ClinicReview(Long id, Clinic clinic, Patient patient, double rating) {
		super();
		this.id = id;
		this.clinic = clinic;
		this.patient = patient;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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
		return "ClinicReview [id=" + id + ", clinic=" + clinic + ", patient=" + patient + ", rating=" + rating + "]";
	}
	
	

}
