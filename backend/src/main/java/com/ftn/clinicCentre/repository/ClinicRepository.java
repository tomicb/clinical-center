package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Clinic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
		
	List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingDesc(String name, String address);
	List<Clinic> findClinicByNameContainingAndAddressContainingOrderByRatingAsc(String name, String address);
}
