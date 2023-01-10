package com.ftn.clinicCentre.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftn.clinicCentre.dto.DoctorDTO;
import com.ftn.clinicCentre.dto.NurseDTO;
import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.ftn.clinicCentre.dto.ClinicDTO;
import com.ftn.clinicCentre.dto.ClinicReviewDTO;

@CrossOrigin
@RestController
@RequestMapping(value="api/clinics")
public class ClinicController {
	
	@Autowired
	private ClinicService clinicService;
	
	@Autowired
	private ClinicReviewService clinicReviewService;
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private NurseService nurseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ClinicAdministratorService clinicAdminService;


	@GetMapping
	public ResponseEntity<?> getClinics(){
		
		List<Clinic> clinics = clinicService.findAll();

		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		
		for(Clinic clinic: clinics) {
			clinicsDTO.add(new ClinicDTO(clinic.getId(), clinic.getName(), clinic.getAddress(), clinic.getRating()));
		}
		
		Map<String, Object> answer = new HashMap<>();
		answer.put("clinics", clinicsDTO);

		
		return new ResponseEntity<>(answer, HttpStatus.OK);
	}
	
	@GetMapping(value="/searched")
	public ResponseEntity<?> getSearchedClinics(
			@RequestParam(value="name", required = false) String name,
			@RequestParam(value="address", required = false) String address,
			@RequestParam(defaultValue="descending", required = false) String sort
			){
		
		if(name == null) {
			name = "";
		}
		
		if(address == null) {
			address = "";
		}
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		
		if(sort.equals("descending")) {
			List<Clinic> clinics = clinicService.findClinicByNameContainingAndAddressContainingOrderByRatingDesc(name, address);

			for(Clinic c: clinics) {
				clinicsDTO.add(new ClinicDTO(c.getId(),c.getName(),c.getAddress(),c.getRating()));
			}			
		}
		
		if(sort.equals("ascending")) {
			List<Clinic> clinics = clinicService.findClinicByNameContainingAndAddressContainingOrderByRatingAsc(name, address);

			for(Clinic c: clinics) {
				clinicsDTO.add(new ClinicDTO(c.getId(),c.getName(),c.getAddress(),c.getRating()));
			}	
		}
		
		Map<String, Object> answer = new HashMap<>();
		answer.put("clinics", clinicsDTO);
		return new ResponseEntity<>(answer, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClinicDTO> getOneClinic(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		ClinicDTO clinicDTO = new ClinicDTO(clinic.getId(), clinic.getName(), clinic.getAddress(), clinic.getDescription(), clinic.getRating(), clinic.getPriceList());

		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "adminsClinic")
	public ResponseEntity<ClinicDTO> getAdminsClinic(){
		User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	ClinicAdministrator ca = clinicAdminService.findByJmbg(user.getJmbg());
    	if(ca == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	return new ResponseEntity<ClinicDTO>(new ClinicDTO(ca.getClinic()), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/doctors")
	public ResponseEntity<List<DoctorDTO>> getClinicDoctors(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Doctor> doctors = doctorService.findDoctorsByClinic(clinic);
		List<DoctorDTO> doctorDTOs = new ArrayList<>();
		for(Doctor doctor: doctors) {
			doctorDTOs.add(new DoctorDTO(doctor));
		}

		return new ResponseEntity<>(doctorDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/nurses")
	public ResponseEntity<List<NurseDTO>> getClinicNurses(@PathVariable(value = "id") Long id) {

		Clinic clinic = clinicService.findById(id);
		if(clinic == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<Nurse> nurses = nurseService.findNursesByClinic(clinic);
		List<NurseDTO> nurseDTOs = new ArrayList<>();
		for(Nurse nurse: nurses) {
			nurseDTOs.add(new NurseDTO(nurse));
		}
		return new ResponseEntity<>(nurseDTOs, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PostMapping
	public ResponseEntity<ClinicDTO> saveClinic(@RequestBody ClinicDTO clinicDTO) {

		if(clinicDTO.getName().strip().equals("") || clinicDTO.getName() == null ||
			clinicDTO.getAddress().strip().equals("") || clinicDTO.getAddress() == null ||
			clinicDTO.getDescription().strip().equals("") || clinicDTO.getDescription() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Clinic newClinic = new Clinic(clinicDTO.getName(),  clinicDTO.getAddress(), clinicDTO.getDescription(), clinicDTO.getPriceList(), 0.0);
		newClinic = clinicService.save(newClinic);

		clinicDTO.setId(newClinic.getId());

		return new ResponseEntity<>(clinicDTO, HttpStatus.CREATED);
	}

	@PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
	@PutMapping
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO) {

		Clinic clinic = clinicService.findById(clinicDTO.getId());

		if(clinic == null ||
			clinicDTO.getName().strip().equals("") || clinicDTO.getName() == null ||
			clinicDTO.getAddress().strip().equals("") || clinicDTO.getAddress() == null ||
			clinicDTO.getDescription().strip().equals("") || clinicDTO.getDescription() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		clinic = new Clinic(clinicDTO.getId(), clinicDTO.getName(),  clinicDTO.getAddress(), clinicDTO.getDescription(), clinicDTO.getPriceList(), clinic.getRating());
		clinicService.save(clinic);

		return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_PATIENT')")
	@PostMapping(value = "/evaluate")
	public ResponseEntity<ClinicReviewDTO> evaluateClinic(@RequestBody ClinicReviewDTO clinicReviewDTO){
		
		Patient patient = patientService.findOne("2403982698510");
		Clinic clinic = clinicService.findById(clinicReviewDTO.getClinicId());
		
		ClinicReview review = new ClinicReview(clinic, patient, clinicReviewDTO.getRating());
		
		clinicReviewService.save(review);
		
		List<ClinicReview> allReviews = clinicReviewService.findAll();
		int counter = 0;
		int sum = 0;
		for(ClinicReview cr : allReviews) {
			if(cr.getClinic().getId().equals(clinic.getId())) {
				sum += cr.getRating();
				counter++;
			}
		}
		double avg = (double)sum/(double)counter;
		clinic.setRating(avg);
		clinicService.save(clinic);
		
		return new ResponseEntity<ClinicReviewDTO>(clinicReviewDTO ,HttpStatus.OK);
		
	}
}
