package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.ClinicAdministrator;
import com.ftn.clinicCentre.entity.EGender;

public class ClinicAdministratorDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String jmbg;
	private String address;
	private String email;
	private String password;
	private EGender gender;
	private Long clinicId;

	public ClinicAdministratorDTO() {}

	public ClinicAdministratorDTO(ClinicAdministrator admin) {
		id = admin.getId();
		firstName = admin.getFirstName();
		lastName = admin.getLastName();
		jmbg = admin.getJmbg();
		address = admin.getAddress();
		email = admin.getEmail();
		password = " ".repeat(admin.getPassword().length());
		gender = admin.getGender();

		if(admin.getClinic() != null)
			clinicId = admin.getClinic().getId();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}
