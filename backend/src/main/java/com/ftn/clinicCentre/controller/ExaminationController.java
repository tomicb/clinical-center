package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.ExaminationDTO;
import com.ftn.clinicCentre.dto.PatientDTO;
import com.ftn.clinicCentre.dto.RecipeDTO;
import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/examinations")
public class ExaminationController {
	
	@Autowired
	private PriceListItemService priceListItemRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;

    @Autowired
    private ExaminationService examinationService;
    
    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
	private UserService userService;


    //TODO mozda se ne koristi
    @PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_DOCTOR')")
    @GetMapping
    public ResponseEntity<List<ExaminationDTO>> getExaminations() {
        List<Examination> examinations = examinationService.findExaminationsByApprovedRecipeStatus();

        List<ExaminationDTO> examinationDTOs = new ArrayList<>();
        for(Examination examination: examinations) {
            examinationDTOs.add(new ExaminationDTO(examination));
        }

        return new ResponseEntity<>(examinationDTOs, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/doctorsExaminations")
    public ResponseEntity<List<ExaminationDTO>> getDoctorsExaminations(){
    	User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Doctor doctor = doctorService.findOne(user.getJmbg());
    	if(doctor == null) {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	
    	List<Examination> examinations = examinationService.findExaminationsByDoctor(doctor);
    	
    	List<ExaminationDTO> examinationDTOs = new ArrayList<>();
        for(Examination examination: examinations) {
            examinationDTOs.add(new ExaminationDTO(examination));
        }

        return new ResponseEntity<>(examinationDTOs, HttpStatus.OK);
    	
    }

    @PreAuthorize("hasAnyRole('ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_PATIENT')")
    @GetMapping(value="/available-examinations/{id}")
    public ResponseEntity<List<ExaminationDTO>> getAvailableExaminations(@PathVariable("id") Long id){

    	List<Examination> examinations = examinationService.findAvailableExaminationByClinic(id);
        List<ExaminationDTO> examinationDTOs = new ArrayList<>();
    	for(Examination examination: examinations) {
        		examinationDTOs.add(new ExaminationDTO(examination));
 
    	}
    	
    	return new ResponseEntity<>(examinationDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PutMapping(value = "/available-examination-schedule")
    public ResponseEntity<ExaminationDTO> scheduleAvailable(@RequestBody ExaminationDTO examinationDTO){
    	Examination examination = examinationService.findById(examinationDTO.getId());
    	User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Patient patient = patientService.findOne(user.getJmbg());
    	if(patient == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	examination.setPatient(patient);
    	
    	examination.setTitle(examinationDTO.getTitle());
    	PriceListItem pli = priceListItemRepository.findPriceListItemByName(examinationDTO.getTitle());
    	examination.setPrice(pli.getPrice());
        examinationService.save(examination);
        
        emailSenderService.send(user.getEmail(), "Poštovani/na " + user.getFirstName() + " " + user.getLastName() + ", uspešno ste zakazali pregled. Hvala na poverenju, Vaš klinički centar.", "Informacije o zakazivanju");

        return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.OK);
    	
    }

    //TODO ne koristi se
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_PATIENT')")
    @GetMapping(value="/predefined")
    public ResponseEntity<List<ExaminationDTO>> getPredefinedExaminations(){
    	List<Examination> examinations = examinationService.findAll();
        List<ExaminationDTO> examinationDTOs = new ArrayList<>();
    	for(Examination examination: examinations) {
    		if(examination.getPatient() == null ) {
    			if(examination.getStart().isBefore(LocalDateTime.now())) {
        			examinationDTOs.add(new ExaminationDTO(examination));
    			}
    		}
    	}
    	
    	return new ResponseEntity<>(examinationDTOs, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PutMapping(value = "/predefined-schedule/{id}")
    public ResponseEntity<ExaminationDTO> schedulePredefined(@PathVariable("id") Long id) {

        Examination examination = examinationService.findById(id);
//        if(examination == null || examination.getPatient() != null) {
//            return new ResponseEntity<>(new ExaminationDTO(), HttpStatus.NOT_FOUND);
//        }
        
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    	Patient patient = patientService.findOne(user.getJmbg());
    	if(patient == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        
        examination.setPatient(patient);

        examination = examinationService.save(examination);

        return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_PATIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExaminationDTO> findOne(@PathVariable(value = "id") Long id, Authentication authentication){

        Doctor doctor = doctorService.findOneByEmail(authentication.getName());
        Examination examination =  examinationService.findById(id);
        if(examination == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        ExaminationDTO examinationDTO = new ExaminationDTO(examination);
        if(doctor != null && examination.getDoctor().getId().equals(doctor.getId()))
            examinationDTO.setPermission(true);

        return new ResponseEntity<>(examinationDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_CLINIC_ADMINISTRATOR', 'ROLE_CLINIC_CENTRE_ADMINISTRATOR', 'ROLE_DOCTOR')")
    @GetMapping(value = "/doctor/{jmbg}")
    public ResponseEntity<List<ExaminationDTO>> findOne(@PathVariable(value = "jmbg") String jmbg){
    	
    	Doctor doctor = doctorService.findOne(jmbg);
    	
    	List<Examination> examinations = examinationService.findExaminationsByDoctor(doctor);
    	List<ExaminationDTO> examinationsDTO = new ArrayList<ExaminationDTO>();
    	for(Examination e : examinations) {
    		examinationsDTO.add(new ExaminationDTO(e));
    	}
    	
    	return new ResponseEntity<>(examinationsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ExaminationDTO> createExamination(@RequestBody ExaminationDTO examinationDTO) {
    	System.out.println(examinationDTO.getStart());
    	System.out.println(examinationDTO.getEnd());
    	try {
			String dateOfBirth = examinationDTO.getDoctorJmbg().substring(0, 2);
			String monthOfBirth = examinationDTO.getDoctorJmbg().substring(2, 4);
			String yearOfBirth = examinationDTO.getDoctorJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				return new ResponseEntity<ExaminationDTO>( HttpStatus.BAD_REQUEST);
			}
			
			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ExaminationDTO>( HttpStatus.BAD_REQUEST);	
		}
    	
    	if(examinationDTO.getStart().isBefore(LocalDateTime.now()) || examinationDTO.getEnd().isBefore(LocalDateTime.now())) {
    		return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    	}

//        MedicalRecord medicalRecord = medicalRecordService.findOneByPatient(examinationDTO.getPatient());
//        if(medicalRecord == null) {
//            medicalRecord = new MedicalRecord();
//            medicalRecord.setPatient(examinationDTO.getPatient());
//        }
        
        Doctor doctor = doctorService.findOne(examinationDTO.getDoctorJmbg());

        Examination examination = new Examination(doctor, examinationDTO.getPatient(), examinationDTO.getStart(), examinationDTO.getEnd(), examinationDTO.getPrice(), examinationDTO.getDiscount(), examinationDTO.getReport(), examinationDTO.getTitle());
//        medicalRecord.getExaminations().add(examination);

        examination = examinationService.save(examination);
//        medicalRecordService.save(medicalRecord);

        return new ResponseEntity<>(examinationDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @PutMapping
    public ResponseEntity<ExaminationDTO> updateExamination(@RequestBody Examination examination, Authentication authentication) {

        Doctor doctor = doctorService.findOneByEmail(authentication.getName());
        MedicalRecord medicalRecord = medicalRecordService.findOneByPatient(examination.getPatient());
        if(medicalRecord == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        if(doctor == null || !doctor.getId().equals(examination.getDoctor().getId()))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        examination = examinationService.save(examination);

        return new ResponseEntity<>(new ExaminationDTO(examination), HttpStatus.OK);
    }
}
