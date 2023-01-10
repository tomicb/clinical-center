package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.ERecipeStatus;
import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.entity.Patient;
import com.ftn.clinicCentre.repository.ExaminationRepository;
import com.ftn.clinicCentre.service.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    @Autowired
    ExaminationRepository examinationRepository;

    @Override
    public List<Examination> findAll() {
        return examinationRepository.findAll();
    }

    @Override
    public List<Examination> findExaminationsByApprovedRecipeStatus() {
        return examinationRepository.findAll();
    }

    @Override
    public Examination findById(Long id) {
        return examinationRepository.findById(id).orElse(null);
    }

    @Override
    public Examination save(Examination examination) {
        return examinationRepository.save(examination);
    }

	@Override
	public List<Examination> findExaminationsByDoctor(Doctor doctor) {
		return examinationRepository.findExaminationsByDoctor(doctor);
	}

    @Override
    public List<Examination> findExaminationsByDoctorAndPatient(Doctor doctor, Patient patient) {
        return examinationRepository.findExaminationsByDoctorAndPatient(doctor, patient);
    }

    @Override
	public List<Examination> findAvailableExaminationByClinic(Long id) {
		return examinationRepository.findAvailableExaminationByClinic(id);
	}
}
