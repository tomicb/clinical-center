package com.ftn.clinicCentre.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
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

import com.ftn.clinicCentre.dto.DoctorDTO;
import com.ftn.clinicCentre.dto.DoctorsReviewDTO;

@CrossOrigin
@Controller
@RequestMapping(value = "api/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private DoctorsReviewService doctorsReviewService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ClinicAdministratorService clinicAdminService;

	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping
	public ResponseEntity<List<DoctorDTO>> index() {
		
		List<Doctor> doctors = doctorService.findAll();
		List<DoctorDTO> doctorsDTO = new ArrayList<DoctorDTO>();
		for(Doctor doctor: doctors) {
			doctorsDTO.add(new DoctorDTO(doctor));
		}
		
		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_DOCTOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/{jmbg}")
	public ResponseEntity<DoctorDTO> findOne(@PathVariable(value = "jmbg") String jmbg){
		
		Doctor doctor = doctorService.findOne(jmbg);
		if(doctor == null) {
			return new ResponseEntity<DoctorDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<DoctorDTO>(new DoctorDTO(doctor), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_DOCTOR')")
	@GetMapping(value = "/doctorInfo")
	public ResponseEntity<DoctorDTO> findOne(){
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Doctor doctor = doctorService.findOne(user.getJmbg());
		return new ResponseEntity<DoctorDTO>(new DoctorDTO(doctor), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@GetMapping(value = "/not/clinic/{id}")
	public ResponseEntity<List<DoctorDTO>> findByNotInClinic(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		List<Doctor> doctors = doctorService.findNotInClinic(clinic);
		List<DoctorDTO> doctorDTOs = new ArrayList<>();
		for (Doctor doctor: doctors) {
			doctorDTOs.add(new DoctorDTO(doctor));
		}

		return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_CLINIC_ADMINISTRATOR')")
	@PostMapping(value = "/add")
	public ResponseEntity<DoctorDTO> createDoctor(@RequestBody Doctor doctor){
		
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	ClinicAdministrator ca = clinicAdminService.findByJmbg(user.getJmbg());
    	if(ca == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
		
    	doctor.setClinic(ca.getClinic());
    	
		doctorService.save(doctor);
		
		return new ResponseEntity<DoctorDTO>(new DoctorDTO(doctor), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_DOCTOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PutMapping(value = "/update")
	public ResponseEntity<DoctorDTO> updateDoctor(@RequestBody DoctorDTO doctor){
		
		Doctor d = doctorService.findOne(doctor.getJmbg());
		if(d == null) {
			return new ResponseEntity<DoctorDTO>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			String dateOfBirth = doctor.getJmbg().substring(0, 2);
			String monthOfBirth = doctor.getJmbg().substring(2, 4);
			String yearOfBirth = doctor.getJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);
			}

			System.out.println(doctor);
			System.out.println(doctor.getJmbg());
			System.out.println(dateOfBirth);
			System.out.println(monthOfBirth);
			System.out.println(yearOfBirth);

			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			System.out.println(date);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);	
		}
		
		if(doctor.getFirstName().length() < 1) {
			return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);
		}else if(doctor.getLastName().length() < 1) {
			return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);
		}else if(doctor.getAddress().length() < 1) {
			return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);
		}else if(!UserController.validate(doctor.getEmail())) {
			return new ResponseEntity<DoctorDTO>( HttpStatus.BAD_REQUEST);			
		}
		
		d.setFirstName(doctor.getFirstName());
		d.setLastName(doctor.getLastName());
		d.setAddress(doctor.getAddress());
		d.setEmail(doctor.getEmail());
		d.setGender(doctor.getGender());
		if(doctor.getClinicId() != null) {
			Clinic clinic = clinicService.findById(doctor.getClinicId());
			if(clinic != null) {
				d.setClinic(clinic);
			}
		}
		
		doctorService.save(d);
		
		return new ResponseEntity<DoctorDTO>(doctor, HttpStatus.OK);
	}

	//TODO obirsati
	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@DeleteMapping(value = "/delete/{jmbg}")
	public ResponseEntity<?> deleteDoctor(@PathVariable(value = "jmbg") String jmbg){
		
		Doctor doctor = doctorService.findOne(jmbg);
		
		if(doctor != null) {
			doctorService.delete(doctor);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}

	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@PostMapping(value = "/evaluate")
	public ResponseEntity<DoctorsReviewDTO> evaluateDoctor(@RequestBody DoctorsReviewDTO doctorReviewDTO){
		
		Patient patient = patientService.findOne("2403982698510");
		Doctor doctor = doctorService.findOne(doctorReviewDTO.getDoctorJmbg());
		
		DoctorsReview review = new DoctorsReview(doctor, patient, doctorReviewDTO.getRating());
		
		doctorsReviewService.save(review);
		
		List<DoctorsReview> allReviews = doctorsReviewService.findAll();
		int counter = 0;
		int sum = 0;
		for(DoctorsReview dr : allReviews) {
			if(dr.getDoctor().getId().equals(doctor.getId())) {
				sum += dr.getRating();
				counter++;
			}
		}
		double avg = (double)sum/(double)counter;
		doctor.setRating(avg);
		doctorService.save(doctor);
		
		return new ResponseEntity<DoctorsReviewDTO>(doctorReviewDTO, HttpStatus.OK);
	}

}
