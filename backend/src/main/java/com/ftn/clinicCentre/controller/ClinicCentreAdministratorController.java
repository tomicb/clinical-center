package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.ClinicCentreAdministratorDTO;
import com.ftn.clinicCentre.entity.ClinicCentreAdministrator;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.service.AuthorityService;
import com.ftn.clinicCentre.service.ClinicCentreAdministratorService;
import com.ftn.clinicCentre.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "api/clinic-centre-administrators")
public class ClinicCentreAdministratorController {

    @Autowired
    private ClinicCentreAdministratorService clinicCentreAdministratorService;
    
    @Autowired
	private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<ClinicCentreAdministratorDTO>> allAdmins(){

        List<ClinicCentreAdministrator> admins = clinicCentreAdministratorService.findAll();
        List<ClinicCentreAdministratorDTO> adminsDTO = new ArrayList<>();

        for(ClinicCentreAdministrator admin : admins) {
            adminsDTO.add(new ClinicCentreAdministratorDTO(admin));
        }

        return new ResponseEntity<>(adminsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClinicCentreAdministratorDTO> findOne(@PathVariable(value = "id") Long id){

        ClinicCentreAdministrator clinicCentreAdministrator = clinicCentreAdministratorService.findById(id);

        if(clinicCentreAdministrator == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ClinicCentreAdministratorDTO(clinicCentreAdministrator), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping(value = "/clinicCentreAdminInfo")
    public ResponseEntity<ClinicCentreAdministratorDTO> findOne(){
    	
    	User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

        ClinicCentreAdministrator clinicCentreAdministrator = clinicCentreAdministratorService.findById(user.getId());

        if(clinicCentreAdministrator == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ClinicCentreAdministratorDTO(clinicCentreAdministrator), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<ClinicCentreAdministratorDTO> createAdmin(@RequestBody ClinicCentreAdministratorDTO clinicCentreAdministratorDTO){

        if(clinicCentreAdministratorDTO.getFirstName().strip().equals("") ||
                clinicCentreAdministratorDTO.getLastName().strip().equals("") ||
                clinicCentreAdministratorDTO.getJmbg().strip().length() != 13 ||
                clinicCentreAdministratorService.findByJmbg(clinicCentreAdministratorDTO.getJmbg().strip()) != null ||
                clinicCentreAdministratorDTO.getAddress().strip().equals("") ||
                !UserController.validate(clinicCentreAdministratorDTO.getEmail().strip()) ||
                clinicCentreAdministratorService.findByEmail(clinicCentreAdministratorDTO.getEmail().strip().toLowerCase()) != null ||
                clinicCentreAdministratorDTO.getPassword() == null ||
                clinicCentreAdministratorDTO.getPassword().length() < 6)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            String jmbg = clinicCentreAdministratorDTO.getJmbg().strip();
            String DD = jmbg.substring(0,2);
            String MM = jmbg.substring(2,4);

            String GGGG = "1" + jmbg.substring(4,7);
            if(jmbg.substring(4,5).equals("0"))
                GGGG = "2" + jmbg.substring(4,7);

            LocalDate.parse(GGGG + "-" + MM + "-" + DD);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ClinicCentreAdministrator clinicCentreAdministrator = new ClinicCentreAdministrator(
                clinicCentreAdministratorDTO.getFirstName().strip(),
                clinicCentreAdministratorDTO.getLastName().strip(),
                clinicCentreAdministratorDTO.getJmbg().strip(),
                clinicCentreAdministratorDTO.getAddress().strip(),
                clinicCentreAdministratorDTO.getEmail().strip().toLowerCase(),
                passwordEncoder.encode(clinicCentreAdministratorDTO.getPassword()),
                clinicCentreAdministratorDTO.getGender(),
                authorityService.findByName("ROLE_CLINIC_CENTRE_ADMINISTRATOR"),
                EUserStatus.FIRST_LOGGIN);

        clinicCentreAdministrator = clinicCentreAdministratorService.save(clinicCentreAdministrator);

        return new ResponseEntity<>(new ClinicCentreAdministratorDTO(clinicCentreAdministrator), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @PutMapping
    public ResponseEntity<ClinicCentreAdministratorDTO> upadateAdmin(@RequestBody ClinicCentreAdministratorDTO clinicCentreAdministratorDTO){

        ClinicCentreAdministrator clinicCentreAdministrator = clinicCentreAdministratorService.findById(clinicCentreAdministratorDTO.getId());
        if(clinicCentreAdministrator == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!clinicCentreAdministrator.getEmail().equals(clinicCentreAdministratorDTO.getEmail().strip())) {
            if(clinicCentreAdministratorService.findByEmail(clinicCentreAdministratorDTO.getEmail().strip().toLowerCase()) != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if(!clinicCentreAdministrator.getJmbg().equals(clinicCentreAdministratorDTO.getJmbg().strip())) {
            if(clinicCentreAdministratorService.findByJmbg(clinicCentreAdministratorDTO.getJmbg().strip()) != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        if(clinicCentreAdministratorDTO.getFirstName().strip().equals("") ||
                clinicCentreAdministratorDTO.getLastName().strip().equals("") ||
                clinicCentreAdministratorDTO.getJmbg().strip().length() != 13 ||
                clinicCentreAdministratorDTO.getAddress().strip().equals("") ||
                !UserController.validate(clinicCentreAdministratorDTO.getEmail().strip()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            String jmbg = clinicCentreAdministratorDTO.getJmbg().strip();
            String DD = jmbg.substring(0,2);
            String MM = jmbg.substring(2,4);

            String GGGG = "1" + jmbg.substring(4,7);
            if(jmbg.substring(4,5).equals("0"))
                GGGG = "2" + jmbg.substring(4,7);

            LocalDate.parse(GGGG + "-" + MM + "-" + DD);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        clinicCentreAdministrator.setFirstName(clinicCentreAdministratorDTO.getFirstName().strip());
        clinicCentreAdministrator.setLastName(clinicCentreAdministratorDTO.getLastName().strip());
        clinicCentreAdministrator.setJmbg(clinicCentreAdministratorDTO.getJmbg().strip());
        clinicCentreAdministrator.setAddress(clinicCentreAdministratorDTO.getAddress().strip());
        clinicCentreAdministrator.setEmail(clinicCentreAdministratorDTO.getEmail().strip().toLowerCase());
        clinicCentreAdministrator.setGender(clinicCentreAdministrator.getGender());

        clinicCentreAdministratorService.save(clinicCentreAdministrator);

        return new ResponseEntity<>(new ClinicCentreAdministratorDTO(clinicCentreAdministrator), HttpStatus.OK);
    }

    //TODO REGISTRACIJA GRESKA?
    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping(value = "/check/email/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable(value = "email") String email) {

        ClinicCentreAdministrator clinicCentreAdministrator = clinicCentreAdministratorService.findByEmail(email.strip());
        if(clinicCentreAdministrator == null)
            return new ResponseEntity<>(false, HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    //TODO REGISTRACIJA GRESKA?
    @PreAuthorize("hasRole('ROLE_CLINIC_CENTRE_ADMINISTRATOR')")
    @GetMapping(value = "/check/jmbg/{jmbg}")
    public ResponseEntity<Boolean> checkJmbg(@PathVariable(value = "jmbg") String jmbg) {

        ClinicCentreAdministrator clinicCentreAdministrator = clinicCentreAdministratorService.findByJmbg(jmbg.strip());
        if(clinicCentreAdministrator == null)
            return new ResponseEntity<>(false, HttpStatus.OK);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
