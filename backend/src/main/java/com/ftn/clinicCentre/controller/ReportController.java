package com.ftn.clinicCentre.controller;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.clinicCentre.dto.ClinicDTO;
import com.ftn.clinicCentre.dto.DoctorDTO;
import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.Examination;
import com.ftn.clinicCentre.service.ClinicService;
import com.ftn.clinicCentre.service.DoctorService;
import com.ftn.clinicCentre.service.ExaminationService;

@CrossOrigin
@RestController
@RequestMapping(value = "api/clinic-reports")
public class ReportController {
	
	@Autowired
	ClinicService clinicService;
	
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	ExaminationService examinationService;


	@PreAuthorize("hasRole('ROLE_CLINIC_ADMINISTRATOR')")
	@GetMapping
	public ResponseEntity<?> getReports(){
		
		List<Clinic> clinics = clinicService.findAll();
		List<Doctor> doctors = doctorService.findAll();
		
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		
		for(Doctor doctor : doctors) {
			doctorsDTO.add(new DoctorDTO(doctor));
		}
		
		for(Clinic clinic: clinics) {
			clinicsDTO.add(new ClinicDTO(clinic.getId(), clinic.getName(), clinic.getAddress(), clinic.getRating()));
		}		
				
		Double janIncome = 0.0;
		Double febIncome = 0.0;
		Double marIncome = 0.0;
		Double aprIncome = 0.0;
		Double mayIncome = 0.0;
		Double junIncome = 0.0;
		Double julIncome = 0.0;
		Double augIncome = 0.0;
		Double sepIncome = 0.0;
		Double octIncome = 0.0;
		Double novIncome = 0.0;
		Double decIncome = 0.0;
		
		List<Double> incomes = new ArrayList<>();
		List<Examination> examinations = examinationService.findAll();
		
		
		for(Examination examination: examinations) {	
			if(examination.getEnd().getYear() == Year.now().getValue()) {
				if(examination.getPrice() != null) {
					switch(examination.getEnd().getMonthValue()) {
					case 1:
						janIncome = janIncome + examination.getPrice();
						break;
					case 2:
						febIncome = febIncome + examination.getPrice();
						break;
					case 3:
						marIncome = marIncome + examination.getPrice();
						break;
					case 4:
						aprIncome = aprIncome + examination.getPrice();
						break;
					case 5:
						mayIncome = mayIncome + examination.getPrice();
						break;
					case 6:
						junIncome = junIncome + examination.getPrice();
						break;	
					case 7:
						julIncome = julIncome + examination.getPrice();
						break;	
					case 8:
						augIncome = augIncome + examination.getPrice();
						break;	
					case 9:
						sepIncome = sepIncome + examination.getPrice();
						break;	
					case 10:
						octIncome = octIncome + examination.getPrice();
						break;	
					case 11:
						novIncome = novIncome + examination.getPrice();
						break;	
					case 12:
						decIncome = decIncome + examination.getPrice();
						break;	
					default:
						System.out.println("Err in switch case");				
					}
				}
				
			}
		}
		
		incomes.add(janIncome);
		incomes.add(febIncome);
		incomes.add(marIncome);
		incomes.add(aprIncome);
		incomes.add(mayIncome);
		incomes.add(junIncome);
		incomes.add(julIncome);
		incomes.add(augIncome);
		incomes.add(sepIncome);
		incomes.add(octIncome);
		incomes.add(novIncome);
		incomes.add(decIncome);
		
		
		Map<String, Object> answer = new HashMap<>();
		answer.put("clinics", clinicsDTO);
		answer.put("doctors", doctorsDTO);
		answer.put("incomes", incomes);
		
		return new ResponseEntity<>(answer, HttpStatus.OK);
		}
	

}
