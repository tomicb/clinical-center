package com.ftn.clinicCentre.entity;

import com.ftn.clinicCentre.dto.MedicalRecordItemDTO;
import com.ftn.clinicCentre.security.encryption.AttributeEncryptor;

import javax.persistence.*;

@Entity
@Table(name = "medical_record_item")
public class MedicalRecordItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Convert(converter = AttributeEncryptor.class)
    private String name;

    @Column(name = "value", nullable = false)
    @Convert(converter = AttributeEncryptor.class)
    private String value;

    public MedicalRecordItem() {}

    public MedicalRecordItem(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MedicalRecordItem(MedicalRecordItemDTO medicalRecordItemDTO) {
        this.id = medicalRecordItemDTO.getId();
        this.name = medicalRecordItemDTO.getName();
        this.value = medicalRecordItemDTO.getValue();
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

    @Override
    public String toString() {
        return "MedicalRecordItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
