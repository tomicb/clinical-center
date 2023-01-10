package com.ftn.clinicCentre.service.impl;

import java.util.List;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.clinicCentre.entity.ClinicAdministrator;
import com.ftn.clinicCentre.repository.ClinicAdministratorRepository;
import com.ftn.clinicCentre.service.ClinicAdministratorService;

@Service
public class ClinicAdministratorServiceImpl implements ClinicAdministratorService{
	
	@Autowired
	private ClinicAdministratorRepository clinicAdministratorRepository;

	@Override
	public List<ClinicAdministrator> findAll() {
		return clinicAdministratorRepository.findAll();
	}

	@Override
	public ClinicAdministrator findOne(Long id) {
		return clinicAdministratorRepository.findById(id).orElse(null);
	}

	@Override
	public ClinicAdministrator findByEmail(String email) {
		return clinicAdministratorRepository.findClinicAdministratorByEmail(email);
	}

	@Override
	public ClinicAdministrator findByJmbg(String jmbg) {
		return clinicAdministratorRepository.findClinicAdministratorByJmbg(jmbg);
	}

	@Override
	public List<ClinicAdministrator> findByClinic(Clinic clinic) {
		return clinicAdministratorRepository.findClinicAdministratorByClinic(clinic);
	}

	@Override
	public List<ClinicAdministrator> findNotInClinic(Clinic clinic) {
		return clinicAdministratorRepository.findClinicAdministratorByClinicNot(clinic);
	}

	@Override
	public ClinicAdministrator save(ClinicAdministrator admin) {
		return clinicAdministratorRepository.save(admin);
	}

	@Override
	public void delete(ClinicAdministrator admin) {
		clinicAdministratorRepository.delete(admin);
		
	}

	@Override
	public void insertUserAsClinicAdministrator(User user, Clinic clinic) {
		clinicAdministratorRepository.insertUserAsClinicAdministrator(user.getId(), clinic.getId());
	}

}
