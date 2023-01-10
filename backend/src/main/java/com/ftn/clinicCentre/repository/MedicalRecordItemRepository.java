package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.MedicalRecordItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface MedicalRecordItemRepository extends JpaRepository<MedicalRecordItem, Long> {

    String DELETE_MEDICAL_RECORD_ITEM = "DELETE mris, mri FROM medical_record_items mris, medical_record_item mri WHERE mris.items_id = mri.id AND mri.id = :id";

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = DELETE_MEDICAL_RECORD_ITEM, nativeQuery = true)
    void deleteMedicalRecordItemById(Long id);
}
