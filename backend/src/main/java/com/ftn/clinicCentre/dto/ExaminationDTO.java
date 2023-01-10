package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.entity.Patient;

import java.time.LocalDateTime;

public class ExaminationDTO {

    private Long id;
    private Doctor doctor;
    private Patient patient;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double price;
    private Integer discount;
    private String report;
    private String title;
    private RecipeDTO recipe;
    private String doctorJmbg;
    private boolean permission;

    public ExaminationDTO() {}

    public ExaminationDTO(Examination examination) {
        this.id = examination.getId();
        this.doctor = examination.getDoctor();
        this.patient = examination.getPatient();
        this.start = examination.getStart();
        this.end = examination.getEnd();
        this.price = examination.getPrice();
        this.discount = examination.getDiscount();
        this.report = examination.getReport();
        this.title = examination.getTitle();
        this.doctorJmbg = examination.getDoctor().getJmbg();
        this.permission = false;

        if(examination.getRecipe() != null)
            this.recipe = new RecipeDTO(examination);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RecipeDTO getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeDTO recipe) {
        this.recipe = recipe;
    }

    public String getDoctorJmbg() {
		return doctorJmbg;
	}

	public void setDoctorJmbg(String doctorJmbg) {
		this.doctorJmbg = doctorJmbg;
	}

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "ExaminationDTO{" +
                "id=" + id +
                ", doctor=" + doctor +
                ", patient=" + patient +
                ", start=" + start +
                ", end=" + end +
                ", price=" + price +
                ", discount=" + discount +
                ", report='" + report + '\'' +
                ", title='" + title + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
