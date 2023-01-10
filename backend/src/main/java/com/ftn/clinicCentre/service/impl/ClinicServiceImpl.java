package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.repository.ClinicRepository;
import com.ftn.clinicCentre.service.ClinicService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    @Override
    public Clinic save(Clinic clinic) {
    	return clinicRepository.save(clinic);
    }

	@Override
	public List<Clinic> findAll() {

		return clinicRepository.findAll();
	}

	@Override
	public Clinic findById(Long id) {
		return clinicRepository.findById(id).orElse(null);
	}

	@Override
	public List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingDesc(String name, String address) {
		return clinicRepository.findClinicByNameContainingAndAddressContainingOrderByRatingDesc(name, address);
	}

	@Override
	public List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingAsc(String name, String address) {
		return clinicRepository.findClinicByNameContainingAndAddressContainingOrderByRatingAsc(name, address);
	}
}
