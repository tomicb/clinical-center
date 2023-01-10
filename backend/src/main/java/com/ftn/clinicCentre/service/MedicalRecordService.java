package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.MedicalRecord;
import com.ftn.clinicCentre.entity.Patient;

public interface MedicalRecordService {

    MedicalRecord findOneById(Long id);
    MedicalRecord findOneByPatient(Patient patient);
    MedicalRecord save(MedicalRecord medicalRecord);
}
