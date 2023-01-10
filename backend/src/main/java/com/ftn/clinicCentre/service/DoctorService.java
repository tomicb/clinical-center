package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.User;

public interface DoctorService {

	List<Doctor> findAll();
	List<Doctor> findDoctorsByClinic(Clinic clinic);
	List<Doctor> findNotInClinic(Clinic clinic);
	Doctor findOne(String jmbg);
	Doctor findOneByEmail(String email);
    Doctor save(Doctor doctor);
    void delete(Doctor doctor);
    void insertUserAsDoctor(User user, Clinic clinic);
}
