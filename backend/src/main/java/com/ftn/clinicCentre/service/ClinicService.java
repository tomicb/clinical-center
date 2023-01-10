package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Patient;

public interface ClinicService {

	Clinic findById(Long id);
	List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingDesc(String name, String address);
	List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingAsc(String name, String address);
	List<Clinic> findAll();
    Clinic save(Clinic clinic);
}
