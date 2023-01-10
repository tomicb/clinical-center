package com.ftn.clinicCentre.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Examination> examinations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<MedicalRecordItem> items = new ArrayList<>();

    public MedicalRecord() {}

    public MedicalRecord(Long id) {
        this.id = id;
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

    public List<Examination> getExaminations() {
        return examinations;
    }

    public void setExaminations(List<Examination> examinations) {
        this.examinations = examinations;
    }

    public List<MedicalRecordItem> getItems() {
        return items;
    }

    public void setItems(List<MedicalRecordItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", patient=" + patient +
                ", examinations=" + examinations +
                ", items=" + items +
                '}';
    }
}
