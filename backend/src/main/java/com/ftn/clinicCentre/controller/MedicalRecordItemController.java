package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.MedicalRecordItemDTO;
import com.ftn.clinicCentre.entity.MedicalRecord;
import com.ftn.clinicCentre.entity.MedicalRecordItem;
import com.ftn.clinicCentre.service.MedicalRecordItemService;
import com.ftn.clinicCentre.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(value = "api/medical-record-items")
public class MedicalRecordItemController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    MedicalRecordItemService medicalRecordItemService;

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PostMapping
    public ResponseEntity<MedicalRecordItemDTO> saveMedicalRecordItem(@RequestBody MedicalRecordItemDTO medicalRecordItemDTO) {

        MedicalRecord medicalRecord = medicalRecordService.findOneById(medicalRecordItemDTO.getMedicalRecord());
        if(medicalRecord == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        MedicalRecordItem medicalRecordItem = medicalRecordItemService.save(new MedicalRecordItem(medicalRecordItemDTO));

        medicalRecord.getItems().add(medicalRecordItem);
        medicalRecordService.save(medicalRecord);

        return new ResponseEntity<>(new MedicalRecordItemDTO(medicalRecordItem), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping
    public ResponseEntity<MedicalRecordItemDTO> updateMedicalRecordItem(@RequestBody MedicalRecordItemDTO medicalRecordItemDTO) {

        System.out.println(medicalRecordItemDTO);

        MedicalRecord medicalRecord = medicalRecordService.findOneById(medicalRecordItemDTO.getMedicalRecord());
        if(medicalRecord == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        medicalRecordItemService.save(new MedicalRecordItem(medicalRecordItemDTO));

        return new ResponseEntity<>(medicalRecordItemDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MedicalRecordItemDTO> deleteMedicalRecordItem(@PathVariable("id") Long id) {

        MedicalRecordItem medicalRecordItem = medicalRecordItemService.findOneById(id);
        if(medicalRecordItem == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        medicalRecordItemService.deleteMedicalRecordItemById(medicalRecordItem);

        return new ResponseEntity<>(new MedicalRecordItemDTO(medicalRecordItem), HttpStatus.OK);
    }
}
