package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.entity.MedicalRecordItem;
import com.ftn.clinicCentre.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDTO {

    private Long id;
    private Patient patient;
    private List<ExaminationDTO> examinations = new ArrayList<>();
    private List<MedicalRecordItem> items = new ArrayList<>();
    private boolean permision;

    public MedicalRecordDTO(Long id, Patient patient) {
        this.id = id;
        this.patient = patient;
        this.permision = false;
    }

    public MedicalRecordDTO(Long id, Patient patient, List<Examination> examinations, List<MedicalRecordItem> items) {
        this.id = id;
        this.patient = patient;

        for (Examination examination: examinations) {
            this.examinations.add(new ExaminationDTO(examination));
        }

        this.items = items;
        this.permision = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<ExaminationDTO> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<ExaminationDTO> examinations) {
        this.examinations = examinations;
    }

    public List<MedicalRecordItem> getItems() {
        return items;
    }

    public void setItems(List<MedicalRecordItem> items) {
        this.items = items;
    }

    public boolean isPermision() {
        return permision;
    }

    public void setPermision(boolean permision) {
        this.permision = permision;
    }
}
