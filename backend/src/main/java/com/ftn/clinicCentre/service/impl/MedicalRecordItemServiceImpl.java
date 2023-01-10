package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.MedicalRecordItem;
import com.ftn.clinicCentre.repository.MedicalRecordItemRepository;
import com.ftn.clinicCentre.service.MedicalRecordItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordItemServiceImpl implements MedicalRecordItemService {

    @Autowired
    MedicalRecordItemRepository medicalRecordItemRepository;

    @Override
    public MedicalRecordItem save(MedicalRecordItem medicalRecordItem) {
        return medicalRecordItemRepository.save(medicalRecordItem);
    }

    @Override
    public MedicalRecordItem findOneById(Long id) {
        return medicalRecordItemRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteMedicalRecordItemById(MedicalRecordItem medicalRecordItem) {
        medicalRecordItemRepository.deleteMedicalRecordItemById(medicalRecordItem.getId());
    }
}
