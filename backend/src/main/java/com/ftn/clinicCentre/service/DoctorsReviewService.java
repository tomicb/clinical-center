package com.ftn.clinicCentre.service;

import java.util.List;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.DoctorsReview;

public interface DoctorsReviewService {
	
	List<DoctorsReview> findAll();
	DoctorsReview save(DoctorsReview doctorsReview);
	List<DoctorsReview> findByDoctor(Doctor doctor);
}
