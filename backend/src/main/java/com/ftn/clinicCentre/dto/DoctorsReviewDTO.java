package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.DoctorsReview;

public class DoctorsReviewDTO {

	private String doctorJmbg;
	private String patientJmbg;
	private double rating;
	
	public DoctorsReviewDTO() {}
	
	public DoctorsReviewDTO(DoctorsReview doctorReview) {
		doctorJmbg = doctorReview.getDoctor().getJmbg();
		patientJmbg = doctorReview.getPatient().getJmbg();
		rating = doctorReview.getRating();
	}

	public DoctorsReviewDTO(String doctorJmbg, String patientJmbg, double rating) {
		super();
		this.doctorJmbg = doctorJmbg;
		this.patientJmbg = patientJmbg;
		this.rating = rating;
	}

	public String getDoctorJmbg() {
		return doctorJmbg;
	}

	public void setDoctorJmbg(String doctorJmbg) {
		this.doctorJmbg = doctorJmbg;
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
		return "DoctorReviewDTO [doctorJmbg=" + doctorJmbg + ", patientJmbg=" + patientJmbg + ", rating=" + rating
				+ "]";
	}
	
	
}
