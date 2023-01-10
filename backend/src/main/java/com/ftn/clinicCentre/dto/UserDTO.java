package com.ftn.clinicCentre.dto;
import com.ftn.clinicCentre.entity.EGender;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;

public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private String address;
    private String email;
    private EGender gender;
    private EUserStatus status;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.jmbg = user.getJmbg();
        this.address = user.getAddress();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.status = user.getStatus();
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


    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }
}
