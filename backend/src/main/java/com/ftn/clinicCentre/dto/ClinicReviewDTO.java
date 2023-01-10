package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.ClinicReview;

public class ClinicReviewDTO {
	
	private Long clinicId;
	private String patientJmbg;
	private double rating;
	
	public ClinicReviewDTO() {}

	public ClinicReviewDTO(Long clinicId, String patientJmbg, double rating) {
		super();
		this.clinicId = clinicId;
		this.patientJmbg = patientJmbg;
		this.rating = rating;
	}
	
	public ClinicReviewDTO(ClinicReview cr) {
		this.clinicId = cr.getClinic().getId();
		this.patientJmbg = cr.getPatient().getJmbg();
		this.rating = cr.getRating();
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public String getPatientJmbg() {
		return patientJmbg;
	}

	public void setPatientJmbg(String patientJmbg) {
		this.patientJmbg = patientJmbg;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ClinicReviewDTO [clinicId=" + clinicId + ", patientJmbg=" + patientJmbg + ", rating=" + rating + "]";
	}
	
	

}
