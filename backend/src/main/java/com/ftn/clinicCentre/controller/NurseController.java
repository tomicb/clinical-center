package com.ftn.clinicCentre.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.entity.Clinic;
import com.ftn.clinicCentre.entity.ClinicAdministrator;
import com.ftn.clinicCentre.service.ClinicAdministratorService;
import com.ftn.clinicCentre.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.clinicCentre.dto.NurseDTO;
import com.ftn.clinicCentre.dto.PatientDTO;
import com.ftn.clinicCentre.service.NurseService;
import com.ftn.clinicCentre.service.UserService;

@CrossOrigin
@Controller
@RequestMapping(value = "api/nurses")
public class NurseController {
	
	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ClinicAdministratorService clinicAdminService;

	//TODO nije potrebna
	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping
	public ResponseEntity<List<NurseDTO>> index() {
		
		List<Nurse> nurses =  nurseService.findAll();
		List<NurseDTO> nursesDTO = new ArrayList<NurseDTO>();
		
		for(Nurse n : nurses) {
			nursesDTO.add(new NurseDTO(n));
		}
		
		return new ResponseEntity<>(nursesDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_NURSE', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/{jmbg}")
	public ResponseEntity<NurseDTO> findNurse(@PathVariable(value = "jmbg") String jmbg){
		Nurse nurse = nurseService.findOne(jmbg);
		if(nurse == null) {
			return new ResponseEntity<NurseDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<NurseDTO>(new NurseDTO(nurse), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_NURSE')")
	@GetMapping(value = "/nurseInfo")
	public ResponseEntity<NurseDTO> findNurse(){
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Nurse nurse = nurseService.findOne(user.getJmbg());
		return new ResponseEntity<NurseDTO>(new NurseDTO(nurse), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/not/clinic/{id}")
	public ResponseEntity<List<NurseDTO>> findByNotInClinic(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<Nurse> nurses = nurseService.findNotInClinic(clinic);
		List<NurseDTO> nurseDTOs = new ArrayList<>();
		for (Nurse nurse: nurses) {
			nurseDTOs.add(new NurseDTO(nurse));
		}

		return new ResponseEntity<>(nurseDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR')")
	@PostMapping(value = "/add")
	public ResponseEntity<NurseDTO> createNurse(@RequestBody Nurse nurse){

		nurse.setStatus(EUserStatus.FIRST_LOGGIN);
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	ClinicAdministrator ca = clinicAdminService.findByJmbg(user.getJmbg());
    	if(ca == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
		
    	nurse.setClinic(ca.getClinic());
    	
		nurseService.save(nurse);
		
		return new ResponseEntity<>(new NurseDTO(nurse), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PutMapping(value = "/update")
	public ResponseEntity<NurseDTO> updateNurse(@RequestBody NurseDTO nurse){
		
		Nurse n = nurseService.findOne(nurse.getJmbg());
		if(n == null) {
			return new ResponseEntity<NurseDTO>(HttpStatus.BAD_REQUEST);
		}
		try {
			String dateOfBirth = nurse.getJmbg().substring(0, 2);
			String monthOfBirth = nurse.getJmbg().substring(2, 4);
			String yearOfBirth = nurse.getJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);
			}
			
			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			System.out.println(date);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);	
		}
		
		if(nurse.getFirstName().length() < 1) {
			return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);
		}else if(nurse.getLastName().length() < 1) {
			return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);
		}else if(nurse.getAddress().length() < 1) {
			return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);
		}else if(!UserController.validate(nurse.getEmail())) {
			return new ResponseEntity<NurseDTO>( HttpStatus.BAD_REQUEST);			
		}
		
		n.setFirstName(nurse.getFirstName());
		n.setLastName(nurse.getLastName());
		n.setAddress(nurse.getAddress());
		n.setEmail(nurse.getEmail());
		n.setGender(nurse.getGender());
		if(nurse.getClinicId() != null) {
			Clinic clinic = clinicService.findById(nurse.getClinicId());
			if(clinic != null) {
				n.setClinic(clinic);
			}
		}
		
		nurseService.save(n);
		
		return new ResponseEntity<>(nurse, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR')")
	@DeleteMapping(value = "/delete/{jmbg}")
	public ResponseEntity<?> deleteNurse(@PathVariable(value = "jmbg") String jmbg){
			
		Nurse nurse =  nurseService.findOne(jmbg);
		
		if(nurse != null) {
			nurseService.delete(nurse);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
}
