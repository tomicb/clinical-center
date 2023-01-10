package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {
	
	Patient findPatientById(Long id);
	Patient findPatientByJmbg(String jmbg);
	List<Patient> findPatientByFirstNameContainingAndLastNameContainingAndJmbgContaining(String firstName, String lastName, String jmbg);
}
