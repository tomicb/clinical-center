package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Nurse;

public interface NurseService {

	List<Nurse> findAll();
	List<Nurse> findNursesByClinic(Clinic clinic);
	List<Nurse> findNotInClinic(Clinic clinic);
	Nurse findOne(String jmbg);
	Nurse findOneByEmail(String email);
    Nurse save(Nurse nurse);
    void delete(Nurse nurse);
}
