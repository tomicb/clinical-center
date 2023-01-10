package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	String INSERT_USER_AS_DOCTOR = "INSERT INTO doctor(id, clinic_id, rating) VALUES( :userId , :clinicId , 0)";
	
	Optional<Doctor> findDoctorByJmbg(String jmbg);
	Optional<Doctor> findDoctorByEmail(String email);
	List<Doctor> findDoctorsByClinic(Clinic clinic);
	List<Doctor> findDoctorsByClinicNot(Clinic clinic);

	@Transactional
	@Modifying
	@Query(value = INSERT_USER_AS_DOCTOR, nativeQuery = true)
	void insertUserAsDoctor(Long userId, Long clinicId);
}
