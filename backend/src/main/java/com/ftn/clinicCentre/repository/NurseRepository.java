package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NurseRepository extends JpaRepository<Nurse, String> {

	Optional<Nurse> findNurseByJmbg(String jmbg);
	Optional<Nurse> findNurseByEmail(String email);
	List<Nurse> findNursesByClinic(Clinic clinic);
	List<Nurse> findNursesByClinicNot(Clinic clinic);
}
