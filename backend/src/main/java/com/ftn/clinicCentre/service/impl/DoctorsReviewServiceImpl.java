package com.ftn.clinicCentre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.DoctorsReview;
import com.ftn.clinicCentre.repository.DoctorsReviewRepository;
import com.ftn.clinicCentre.service.DoctorsReviewService;

@Service
public class DoctorsReviewServiceImpl implements DoctorsReviewService {
	
	@Autowired
	private DoctorsReviewRepository repository;

	@Override
	public DoctorsReview save(DoctorsReview doctorsReview) {
		// TODO Auto-generated method stub
		return repository.save(doctorsReview);
	}

	@Override
	public List<DoctorsReview> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<DoctorsReview> findByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return repository.findByDoctor(doctor);
	}

}
