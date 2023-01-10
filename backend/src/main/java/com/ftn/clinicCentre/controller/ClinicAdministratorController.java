package com.ftn.clinicCentre.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.AuthorityService;
import com.ftn.clinicCentre.service.ClinicService;
import com.ftn.clinicCentre.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.clinicCentre.dto.ClinicAdministratorDTO;
import com.ftn.clinicCentre.service.ClinicAdministratorService;

@CrossOrigin
@Controller
@RequestMapping(value = "api/clinic-administrators")
public class ClinicAdministratorController {
	
	@Autowired
	private ClinicAdministratorService clinicAdministratorService;

	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping
	public ResponseEntity<List<ClinicAdministratorDTO>> allAdmins(){
		
		List<ClinicAdministrator> admins = clinicAdministratorService.findAll();
		List<ClinicAdministratorDTO> adminsDTO = new ArrayList<ClinicAdministratorDTO>();
		
		for(ClinicAdministrator admin : admins) {
			adminsDTO.add(new ClinicAdministratorDTO(admin));
		}
		
		return new ResponseEntity<List<ClinicAdministratorDTO>>(adminsDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClinicAdministratorDTO> findOne(@PathVariable(value = "id") Long id){

		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findOne(id);

		if(clinicAdministrator == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR')")
	@GetMapping(value = "/clinicAdminInfo")
	public ResponseEntity<ClinicAdministratorDTO> findOne(){
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	

		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findOne(user.getId());

		if(clinicAdministrator == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/clinic/{id}")
	public ResponseEntity<List<ClinicAdministratorDTO>> findByClinic(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<ClinicAdministratorDTO> clinicAdministratorDTOs = new ArrayList<>();
		List<ClinicAdministrator> clinicAdministrators = clinicAdministratorService.findByClinic(clinic);
		for (ClinicAdministrator clinicAdministrator: clinicAdministrators) {
			clinicAdministratorDTOs.add(new ClinicAdministratorDTO(clinicAdministrator));
		}

		return new ResponseEntity<>(clinicAdministratorDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/not/clinic/{id}")
	public ResponseEntity<List<ClinicAdministratorDTO>> findByNotInClinic(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<ClinicAdministrator> clinicAdministrators = clinicAdministratorService.findNotInClinic(clinic);
		List<ClinicAdministratorDTO> clinicAdministratorDTOs = new ArrayList<>();
		for (ClinicAdministrator clinicAdministrator: clinicAdministrators) {
			clinicAdministratorDTOs.add(new ClinicAdministratorDTO(clinicAdministrator));
		}

		return new ResponseEntity<>(clinicAdministratorDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PostMapping
	public ResponseEntity<ClinicAdministratorDTO> createAdmin(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO){

		Clinic clinic = clinicService.findById(clinicAdministratorDTO.getClinicId());
		if(clinic == null ||
				clinicAdministratorDTO.getFirstName().strip().equals("") ||
				clinicAdministratorDTO.getLastName().strip().equals("") ||
				clinicAdministratorDTO.getJmbg().strip().length() != 13 ||
				clinicAdministratorService.findByJmbg(clinicAdministratorDTO.getJmbg().strip()) != null ||
				clinicAdministratorDTO.getAddress().strip().equals("") ||
				!UserController.validate(clinicAdministratorDTO.getEmail().strip()) ||
				clinicAdministratorService.findByEmail(clinicAdministratorDTO.getEmail().strip().toLowerCase()) != null ||
				clinicAdministratorDTO.getPassword() == null ||
				clinicAdministratorDTO.getPassword().length() < 6)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			String jmbg = clinicAdministratorDTO.getJmbg().strip();
			String DD = jmbg.substring(0,2);
			String MM = jmbg.substring(2,4);

			String GGGG = "1" + jmbg.substring(4,7);
			if(jmbg.substring(4,5).equals("0"))
				GGGG = "2" + jmbg.substring(4,7);

			LocalDate.parse(GGGG + "-" + MM + "-" + DD);
		} catch (DateTimeParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ClinicAdministrator clinicAdministrator = new ClinicAdministrator(
				clinicAdministratorDTO.getFirstName().strip(),
				clinicAdministratorDTO.getLastName().strip(),
				clinicAdministratorDTO.getJmbg().strip(),
				clinicAdministratorDTO.getAddress().strip(),
				clinicAdministratorDTO.getEmail().strip().toLowerCase(),
				passwordEncoder.encode(clinicAdministratorDTO.getPassword()),
				clinicAdministratorDTO.getGender(),
				authorityService.findByName("ROLE_CLINIC_ADMINISTRATOR"),
				EUserStatus.FIRST_LOGGIN,
				clinic);

		clinicAdministrator = clinicAdministratorService.save(clinicAdministrator);

		return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PutMapping
	public ResponseEntity<ClinicAdministratorDTO> updateAdmin(@RequestBody ClinicAdministratorDTO clinicAdministratorDTO){

		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findOne(clinicAdministratorDTO.getId());
		if(clinicAdministrator == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if(!clinicAdministrator.getEmail().equals(clinicAdministratorDTO.getEmail().strip())) {
			if(clinicAdministratorService.findByEmail(clinicAdministratorDTO.getEmail().strip().toLowerCase()) != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}

		if(!clinicAdministrator.getJmbg().equals(clinicAdministratorDTO.getJmbg().strip())) {
			if(clinicAdministratorService.findByJmbg(clinicAdministratorDTO.getJmbg().strip()) != null) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}


		Clinic clinic = clinicService.findById(clinicAdministratorDTO.getClinicId());
		if(clinic == null ||
			clinicAdministratorDTO.getFirstName().strip().equals("") ||
			clinicAdministratorDTO.getLastName().strip().equals("") ||
			clinicAdministratorDTO.getJmbg().strip().length() != 13 ||
			clinicAdministratorDTO.getAddress().strip().equals("") ||
			!UserController.validate(clinicAdministratorDTO.getEmail().strip()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			String jmbg = clinicAdministratorDTO.getJmbg().strip();
			String DD = jmbg.substring(0,2);
			String MM = jmbg.substring(2,4);

			String GGGG = "1" + jmbg.substring(4,7);
			if(jmbg.substring(4,5).equals("0"))
				GGGG = "2" + jmbg.substring(4,7);

			LocalDate.parse(GGGG + "-" + MM + "-" + DD);
		} catch (DateTimeParseException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}


		clinicAdministrator.setFirstName(clinicAdministratorDTO.getFirstName().strip());
		clinicAdministrator.setLastName(clinicAdministratorDTO.getLastName().strip());
		clinicAdministrator.setJmbg(clinicAdministratorDTO.getJmbg().strip());
		clinicAdministrator.setAddress(clinicAdministratorDTO.getAddress().strip());
		clinicAdministrator.setEmail(clinicAdministratorDTO.getEmail().strip().toLowerCase());
		clinicAdministrator.setGender(clinicAdministratorDTO.getGender());
		clinicAdministrator.setClinic(clinic);

		clinicAdministratorService.save(clinicAdministrator);
		
		return new ResponseEntity<>(new ClinicAdministratorDTO(clinicAdministrator), HttpStatus.OK);
	}

	//TODO CCA? GRESKA KOD REGISTRACIJE
	@PreAuthorize("hasRole('ROLE_CLINIC_ADMINISTRATOR')")
	@GetMapping(value = "/check/email/{email}")
	public ResponseEntity<Boolean> checkEmail(@PathVariable(value = "email") String email) {

		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByEmail(email.strip());

		if(clinicAdministrator == null)
			return new ResponseEntity<>(false, HttpStatus.OK);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	//TODO CCA? GRESKA KOD REGISTRACIJE?
	@PreAuthorize("hasRole('ROLE_CLINIC_ADMINISTRATOR')")
	@GetMapping(value = "/check/jmbg/{jmbg}")
	public ResponseEntity<Boolean> checkJmbg(@PathVariable(value = "jmbg") String jmbg) {

		ClinicAdministrator clinicAdministrator = clinicAdministratorService.findByJmbg(jmbg.strip());

		if(clinicAdministrator == null)
			return new ResponseEntity<>(false, HttpStatus.OK);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
