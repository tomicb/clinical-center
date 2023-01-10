package com.ftn.clinicCentre.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.MedicalRecord;
import com.ftn.clinicCentre.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ftn.clinicCentre.dto.PatientDTO;
import com.ftn.clinicCentre.entity.Patient;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.service.PatientService;
import com.ftn.clinicCentre.service.RegistrationService;
import com.ftn.clinicCentre.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UserService userService;


	@PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_NURSE')")
	@GetMapping(value = "/all")
	public ResponseEntity<List<PatientDTO> > getPatients(){
		List<Patient> patients = patientService.findAll();
		
		List<PatientDTO> patientsDTO = new ArrayList<PatientDTO>();
		for(Patient p : patients) {
			patientsDTO.add(new PatientDTO(p));
		}
		
		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/searched")
	public ResponseEntity<List<PatientDTO> > getSearchedPatients(
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "jmbg", required = false) String jmbg
//			@RequestParam Map<String, String> searchParameters
			){
		
		
		if(firstName == null) {
			firstName = "";
		}
		
		if(lastName == null) {
			lastName = "";
		}
		
		if(jmbg == null) {
			jmbg = "";
		}
		
		List<Patient> patients = patientService.findPatientByFirstNameContainingAndLastNameContainingAndJmbgContaining(firstName, lastName, jmbg);
		
		List<PatientDTO> patientsDTO = new ArrayList<PatientDTO>();
		for(Patient p : patients) {
			patientsDTO.add(new PatientDTO(p));
		}
		
		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}

	//TODO fali null check
	@PreAuthorize("hasAnyRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/{jmbg}")
	public ResponseEntity<PatientDTO> findOne(@PathVariable(value = "jmbg") String jmbg){
		Patient patient =  patientService.findOne(jmbg);
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@GetMapping(value = "/patientInfo")
	public ResponseEntity<PatientDTO> findOne(){
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Patient patient = patientService.findOne(user.getJmbg());
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PostMapping(value = "/add")
	public ResponseEntity<PatientDTO> createPatient(@RequestBody Patient patient){
	
		try {
			String dateOfBirth = patient.getJmbg().substring(0, 2);
			String monthOfBirth = patient.getJmbg().substring(2, 4);
			String yearOfBirth = patient.getJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
			}
			
			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			if(date.isAfter(LocalDate.now())) {
				throw new IllegalStateException("jmbg not valid");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);	
		}
		
		if(patient.getFirstName().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(patient.getLastName().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(patient.getPassword().length() < 6) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(patient.getAddress().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(!UserController.validate(patient.getEmail())) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);			
		}

		patient.setStatus(EUserStatus.FIRST_LOGGIN);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setPatient(patient);
		patientService.save(patient);

		medicalRecordService.save(medicalRecord);

		return new ResponseEntity<PatientDTO>(new PatientDTO(patient), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@DeleteMapping(value = "/delete/{jmbg}")
	public ResponseEntity<?> deletePatient(@PathVariable(value = "jmbg") String jmbg){
		
		Patient patient =  patientService.findOne(jmbg);

		if(patient != null) {
			patientService.delete(patient);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_PATIENT')")
	@PutMapping(value = "/update")
	public ResponseEntity<?> updatePatient(@RequestBody Patient patient){
		
		Patient p = patientService.findOne(patient.getJmbg());
		
		if(p == null) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}
		
		try {
			String dateOfBirth = patient.getJmbg().substring(0, 2);
			String monthOfBirth = patient.getJmbg().substring(2, 4);
			String yearOfBirth = patient.getJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
			}
			
			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			System.out.println(date);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);	
		}
		
		if(patient.getFirstName().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(patient.getLastName().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(patient.getAddress().length() < 1) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);
		}else if(!UserController.validate(patient.getEmail())) {
			return new ResponseEntity<PatientDTO>( HttpStatus.BAD_REQUEST);			
		}
		p.setFirstName(patient.getFirstName());
		p.setLastName(patient.getLastName());
		p.setAddress(patient.getAddress());
		p.setEmail(patient.getEmail());
		p.setGender(patient.getGender());
		
		
		patientService.save(p);
		
		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
		
	}

	@GetMapping(value = "/confirm")
	public String confirmToken(@RequestParam(value = "token") String token) {
		return registrationService.confirmToken(token);
	}
}
