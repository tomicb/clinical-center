package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {

    private Long id;
    private ERecipeStatus status;
    private LocalDateTime created;
    private List<Medication> medication;
    private Doctor doctor;
    private Patient patient;
    private Nurse nurse;
    private LocalDateTime start;
    private LocalDateTime end;

    public RecipeDTO() {}

    public RecipeDTO(Examination examination) {
        this.id = examination.getRecipe().getId();
        this.status = examination.getRecipe().getStatus();
        this.created = examination.getRecipe().getCreated();
        this.doctor = examination.getDoctor();
        this.patient = examination.getPatient();
        this.nurse = examination.getRecipe().getNurse();
        this.start = examination.getStart();
        this.end = examination.getEnd();

        if(this.status != ERecipeStatus.REJECTED) {
            this.medication = examination.getRecipe().getMedication();
        } else {
            this.medication = new ArrayList<>();
        }
    };

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.status = recipe.getStatus();
        this.created = recipe.getCreated();
        this.doctor = recipe.getExamination().getDoctor();
        this.patient = recipe.getExamination().getPatient();
        this.nurse = recipe.getNurse();
        this.start = recipe.getExamination().getStart();
        this.end = recipe.getExamination().getEnd();

        if(this.status != ERecipeStatus.REJECTED) {
            this.medication = recipe.getExamination().getRecipe().getMedication();
        } else {
            this.medication = new ArrayList<>();
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERecipeStatus getStatus() {
        return status;
    }

    public void setStatus(ERecipeStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Medication> getMedication() {
        return medication;
    }

    public void setMedication(List<Medication> medication) {
        this.medication = medication;
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

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
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
}
