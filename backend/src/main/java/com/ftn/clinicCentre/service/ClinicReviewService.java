package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.ClinicReview;

public interface ClinicReviewService {
	
	List<ClinicReview> findAll();
	ClinicReview findOne(Long id);
	ClinicReview save(ClinicReview clinicReview);
}
