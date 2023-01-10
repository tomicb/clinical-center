package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.EGender;

public class DoctorDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private Double rating;
	private String jmbg;
	private String email;
	private String address;
	private Long clinicId;
	private EGender gender;
	
	public DoctorDTO() {}

	public DoctorDTO(String firstName, String lastName, Double rating, String jmbg, String email, EGender gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.jmbg = jmbg;
		this.email = email;
		this.gender = gender;
	}
	
	public DoctorDTO(Doctor doctor) {
		this.id = doctor.getId();
		this.firstName = doctor.getFirstName();
		this.lastName = doctor.getLastName();
		this.rating = doctor.getRating();
		this.jmbg = doctor.getJmbg();
		this.email = doctor.getEmail();
		this.address = doctor.getAddress();
		this.gender = doctor.getGender();

		if(doctor.getClinic() != null)
			clinicId = doctor.getClinic().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public EGender getGender() {
		return gender;
	}

	public void setGender(EGender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "DoctorDTO{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", rating=" + rating +
				", jmbg='" + jmbg + '\'' +
				", email='" + email + '\'' +
				", address='" + address + '\'' +
				", clinicId=" + clinicId +
				'}';
	}
}
