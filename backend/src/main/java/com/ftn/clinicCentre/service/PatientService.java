package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.dto.PatientRegistrationDTO;
import com.ftn.clinicCentre.entity.Patient;

public interface PatientService {
	
	Patient findPatientById(Long id);
	List<Patient> findAll();
	Patient findOne(String jmbg);
	List<Patient> findPatientByFirstNameContainingAndLastNameContainingAndJmbgContaining(String firstName, String lastName, String jmbg);
    Patient save(Patient patient);
    void delete(Patient patient);
    String signUp(PatientRegistrationDTO patientRegistrationDTO);
}
