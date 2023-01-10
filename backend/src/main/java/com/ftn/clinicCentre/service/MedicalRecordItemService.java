package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.MedicalRecordItem;

public interface MedicalRecordItemService {

    MedicalRecordItem save(MedicalRecordItem medicalRecordItem);
    MedicalRecordItem findOneById(Long id);
    void deleteMedicalRecordItemById(MedicalRecordItem medicalRecordItem);
}
