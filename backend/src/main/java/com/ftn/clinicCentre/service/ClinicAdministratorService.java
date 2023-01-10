package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.ClinicAdministrator;
import com.ftn.clinicCentre.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface ClinicAdministratorService {
	
	List<ClinicAdministrator> findAll();
	ClinicAdministrator findOne(Long id);
	ClinicAdministrator findByEmail(String email);
	ClinicAdministrator findByJmbg(String jmbg);
	List<ClinicAdministrator> findByClinic(Clinic clinic);
	List<ClinicAdministrator> findNotInClinic(Clinic clinic);
	ClinicAdministrator save(ClinicAdministrator admin);
	void delete(ClinicAdministrator admin);
	void insertUserAsClinicAdministrator(User user, Clinic clinic);
}
