package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.MedicalRecordDTO;
import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "api/medical-records")
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    ExaminationService examinationService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping
    public ResponseEntity<MedicalRecordDTO> getPatientsMedicalRecord(Authentication authentication) {

        User user = userService.findUserByEmail(authentication.getName());
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Patient patient = patientService.findPatientById(user.getId());
        if(patient == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        MedicalRecord medicalRecord = medicalRecordService.findOneByPatient(patient);
        if(medicalRecord == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO(
                medicalRecord.getId(),
                medicalRecord.getPatient(),
                medicalRecord.getExaminations(),
                medicalRecord.getItems()
        );
        return new ResponseEntity<>(medicalRecordDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_DOCTOR')")
    @GetMapping(value = "/{jmbg}")
    public ResponseEntity<MedicalRecordDTO> findOne(@PathVariable(value = "jmbg") String jmbg, Authentication authentication) {

        User user = userService.findUserByEmail(authentication.getName());
        Patient patient = patientService.findOne(jmbg);
        MedicalRecord medicalRecord = medicalRecordService.findOneByPatient(patient);
        if(user == null || patient == null || medicalRecord == null ||
            (user instanceof Patient && !user.getId().equals(patient.getId())))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        MedicalRecordDTO medicalRecordDTO;
        if(user instanceof Doctor) {
            List<Examination> examinations = examinationService.findExaminationsByDoctorAndPatient((Doctor) user, patient);
            if(examinations.isEmpty()) {
                medicalRecordDTO = new MedicalRecordDTO(
                        medicalRecord.getId(),
                        medicalRecord.getPatient()
                );
                return new ResponseEntity<>(medicalRecordDTO, HttpStatus.OK);
            }
        }

        medicalRecordDTO = new MedicalRecordDTO(
                medicalRecord.getId(),
                medicalRecord.getPatient(),
                medicalRecord.getExaminations(),
                medicalRecord.getItems()
        );
        return new ResponseEntity<>(medicalRecordDTO, HttpStatus.OK);
    }
}
