package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.DoctorRepository;
import com.ftn.clinicCentre.service.DoctorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public Doctor save(Doctor doctor) { return doctorRepository.save(doctor); }

	@Override
	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}

	@Override
	public List<Doctor> findDoctorsByClinic(Clinic clinic) {
		return doctorRepository.findDoctorsByClinic(clinic);
	}

	@Override
	public List<Doctor> findNotInClinic(Clinic clinic) {
		return doctorRepository.findDoctorsByClinicNot(clinic);
	}

	@Override
	public Doctor findOne(String jmbg) {
		return doctorRepository.findDoctorByJmbg(jmbg).orElse(null);
	}

	@Override
	public Doctor findOneByEmail(String email) {
		return doctorRepository.findDoctorByEmail(email).orElse(null);
	}

	@Override
	public void delete(Doctor doctor) {
		doctorRepository.delete(doctor);
		
	}

	@Override
	public void insertUserAsDoctor(User user, Clinic clinic) {
		doctorRepository.insertUserAsDoctor(user.getId(), clinic.getId());
	}
}
