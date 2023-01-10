package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.EGender;
import com.ftn.clinicCentre.entity.Patient;

public class PatientDTO {
	
	private String firstName;
	private String lastName;
	private String jmbg;
	private String address;
	private String email;
	private EGender gender;
	
	public PatientDTO(Patient patient) {
		firstName = patient.getFirstName();
		lastName = patient.getLastName();
		jmbg = patient.getJmbg();
		address = patient.getAddress();
		email = patient.getEmail();
		gender = patient.getGender();
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EGender getGender() {
		return gender;
	}

	public void setGender(EGender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "PatientDTO [firstName=" + firstName + ", lastName=" + lastName + ", jmbg=" + jmbg + ", address="
				+ address + ", email=" + email + ", gender=" + gender + "]";
	}
	
	

}
