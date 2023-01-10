package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import javax.transaction.Transactional;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    String FIND_AVAILABLE_EXAMINATION_BY_CLINIC = "SELECT * FROM examination ex  LEFT JOIN examination_recipe er ON ex.id = er.examination_id LEFT JOIN recipe re ON er.recipe_id = re.id LEFT JOIN doctor dr ON ex.doctor_id = dr.id LEFT JOIN clinic cl ON dr.clinic_id = cl.clinic_id WHERE cl.clinic_id = :id and ex.patient_id is null and ex.start >= now()";

    List<Examination> findExaminationsByDoctor(Doctor doctor);
	
	List<Examination> findExaminationsByDoctorAndPatient(Doctor doctor, Patient patient);

    @Query(value = FIND_AVAILABLE_EXAMINATION_BY_CLINIC, nativeQuery = true)
    List<Examination> findAvailableExaminationByClinic(Long id);
}
