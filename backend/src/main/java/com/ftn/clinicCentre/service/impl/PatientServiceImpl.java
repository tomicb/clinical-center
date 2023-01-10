package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.dto.PatientRegistrationDTO;
import com.ftn.clinicCentre.entity.Authority;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.Patient;
import com.ftn.clinicCentre.repository.PatientRepository;
import com.ftn.clinicCentre.security.token.ConfirmationToken;
import com.ftn.clinicCentre.security.token.TokenUtils;
import com.ftn.clinicCentre.service.AuthorityService;
import com.ftn.clinicCentre.service.ConfirmationTokenService;
import com.ftn.clinicCentre.service.PatientService;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    
    @Autowired
    AuthorityService  authorityService;
    
    @Autowired
    TokenUtils tokenUtils;
    
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    public Patient save(Patient patient) { return patientRepository.save(patient); }

	@Override
	public List<Patient> findPatientByFirstNameContainingAndLastNameContainingAndJmbgContaining(String firstName, String lastName, String jmbg) {
		List<Patient> patients = patientRepository.findPatientByFirstNameContainingAndLastNameContainingAndJmbgContaining(firstName, lastName, jmbg);
		System.out.println(patients);
		
		return patients;
	}

	@Override
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	@Override
	public void delete(Patient patient) {
		patientRepository.delete(patient);
		
	}

	@Override
	public Patient findOne(String jmbg) {
		return patientRepository.findPatientByJmbg(jmbg);
		
	}

	@Override
	public String signUp(PatientRegistrationDTO patientRegistrationDTO) {
		// TODO Auto-generated method stub
		Patient p = patientRepository.findPatientByJmbg(patientRegistrationDTO.getJmbg());
		
		if(p != null) {
			 throw new IllegalStateException("Patient already exists!");
		}
		
		String encodedPassword = passwordEncoder.encode(patientRegistrationDTO.getPassword());
		
		List<Authority> auth = authorityService.findByName("ROLE_PATIENT");
		
		Patient patient = new Patient(patientRegistrationDTO.getFirstName(), patientRegistrationDTO.getLastName(), patientRegistrationDTO.getJmbg(), patientRegistrationDTO.getAddress(), patientRegistrationDTO.getEmail(), encodedPassword, patientRegistrationDTO.getGender(), auth, EUserStatus.PENDING);
		
		patientRepository.save(patient);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(patient.getEmail());
		
		String token = tokenUtils.generateRegistrationToken(userDetails);
		
		ConfirmationToken ct = new ConfirmationToken(token, null, patient);
		confirmationTokenService.save(ct);
		
		return token;
	}

	@Override
	public Patient findPatientById(Long id) {
		// TODO Auto-generated method stub
		return patientRepository.findPatientById(id);
	}
}
