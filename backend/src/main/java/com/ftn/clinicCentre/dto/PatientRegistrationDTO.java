package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.EGender;

public class PatientRegistrationDTO {
	
    private String firstName;
    private String lastName;
    private String jmbg;
    private String address;
    private String email;
    private String password;
    private String repeatedPassword;
    private EGender gender;
    
    public PatientRegistrationDTO() {}

	public PatientRegistrationDTO(String firstName, String lastName, String jmbg, String address, String email,
			String password, String repeatedPassword, EGender gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.jmbg = jmbg;
		this.address = address;
		this.email = email;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.gender = gender;
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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public EGender getGender() {
		return gender;
	}

	public void setGender(EGender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "PatientRegistrationDTO [firstName=" + firstName + ", lastName=" + lastName + ", jmbg=" + jmbg
				+ ", address=" + address + ", email=" + email + ", password=" + password + ", repeatedPassword="
				+ repeatedPassword + ", gender=" + gender + "]";
	}    

}
