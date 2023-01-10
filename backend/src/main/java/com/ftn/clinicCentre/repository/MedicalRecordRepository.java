package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.MedicalRecord;
import com.ftn.clinicCentre.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    MedicalRecord findMedicalRecordByPatient(Patient patient);
}
