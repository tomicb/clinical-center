package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.EGender;
import com.ftn.clinicCentre.entity.Nurse;

public class NurseDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String jmbg;
	private String address;
	private String email;
	private EGender gender;
	private Long clinicId;

	public NurseDTO() {}
	
	public NurseDTO(Nurse nurse) {
		id = nurse.getId();
		firstName = nurse.getFirstName();
		lastName = nurse.getLastName();
		jmbg = nurse.getJmbg();
		address = nurse.getAddress();
		email = nurse.getEmail();
		gender = nurse.getGender();

		if(nurse.getClinic() != null)
			clinicId = nurse.getClinic().getId();
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

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	@Override
	public String toString() {
		return "NurseDTO{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", jmbg='" + jmbg + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", clinicId=" + clinicId +
				'}';
	}
}
