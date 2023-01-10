package com.ftn.clinicCentre.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.clinicCentre.entity.ClinicReview;
import com.ftn.clinicCentre.repository.ClinicReviewRepository;
import com.ftn.clinicCentre.service.ClinicReviewService;

@Service
public class ClinicReviewServiceImpl implements ClinicReviewService{
	
	@Autowired
	private ClinicReviewRepository clinicReviewRepository;

	@Override
	public List<ClinicReview> findAll() {
		// TODO Auto-generated method stub
		return clinicReviewRepository.findAll();
	}

	@Override
	public ClinicReview findOne(Long id) {
		// TODO Auto-generated method stub
		return clinicReviewRepository.findClinicReviewById(id);
	}

	@Override
	public ClinicReview save(ClinicReview clinicReview) {
		// TODO Auto-generated method stub
		return clinicReviewRepository.save(clinicReview);
	}

}
