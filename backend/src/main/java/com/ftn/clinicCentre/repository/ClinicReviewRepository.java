package com.ftn.clinicCentre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.clinicCentre.entity.ClinicReview;

public interface ClinicReviewRepository extends JpaRepository<ClinicReview, Long> {
	
	ClinicReview findClinicReviewById(Long id);

}
