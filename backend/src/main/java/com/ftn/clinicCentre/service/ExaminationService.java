package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.entity.Patient;

import java.util.List;

public interface ExaminationService {

    List<Examination> findAll();
    List<Examination> findExaminationsByApprovedRecipeStatus();
    Examination findById(Long id);
    Examination save(Examination examination);
    List<Examination> findExaminationsByDoctor(Doctor doctor);
    List<Examination> findExaminationsByDoctorAndPatient(Doctor doctor, Patient patient);
    List<Examination> findAvailableExaminationByClinic(Long id);
}
