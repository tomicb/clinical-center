package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.MedicalRecord;
import com.ftn.clinicCentre.entity.Patient;
import com.ftn.clinicCentre.repository.MedicalRecordRepository;
import com.ftn.clinicCentre.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecord findOneById(Long id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }

    @Override
    public MedicalRecord findOneByPatient(Patient patient) {
        return medicalRecordRepository.findMedicalRecordByPatient(patient);
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
}
