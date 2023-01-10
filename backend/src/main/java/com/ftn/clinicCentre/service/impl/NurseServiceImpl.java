package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Nurse;
import com.ftn.clinicCentre.repository.NurseRepository;
import com.ftn.clinicCentre.service.NurseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    NurseRepository nurseRepository;

    @Override
    public Nurse save(Nurse nurse) {
    	return nurseRepository.save(nurse);
    }

	@Override
	public List<Nurse> findAll() {
		return nurseRepository.findAll();
	}

	@Override
	public List<Nurse> findNursesByClinic(Clinic clinic) {
		return nurseRepository.findNursesByClinic(clinic);
	}

	@Override
	public List<Nurse> findNotInClinic(Clinic clinic) {
		return nurseRepository.findNursesByClinicNot(clinic);
	}

	@Override
	public Nurse findOne(String jmbg) {
		return nurseRepository.findNurseByJmbg(jmbg).orElse(null);
	}

	@Override
	public Nurse findOneByEmail(String email) {
		return nurseRepository.findNurseByEmail(email).orElse(null);
	}

	@Override
	public void delete(Nurse nurse) {
		nurseRepository.delete(nurse);
		
	}
}
