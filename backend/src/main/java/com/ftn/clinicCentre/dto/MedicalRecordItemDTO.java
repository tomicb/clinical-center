package com.ftn.clinicCentre.dto;

import com.ftn.clinicCentre.entity.MedicalRecordItem;

public class MedicalRecordItemDTO {

    private Long id;
    private String name;
    private String value;
    private Long medicalRecord;

    public MedicalRecordItemDTO() {}

    public MedicalRecordItemDTO(MedicalRecordItem medicalRecordItem) {
        this.id = medicalRecordItem.getId();
        this.name = medicalRecordItem.getName();
        this.value = medicalRecordItem.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(Long medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
