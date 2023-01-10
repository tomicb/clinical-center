package com.ftn.clinicCentre.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ftn.clinicCentre.controller.UserController;
import com.ftn.clinicCentre.dto.PatientRegistrationDTO;
import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.Patient;
import com.ftn.clinicCentre.security.token.ConfirmationToken;
import com.ftn.clinicCentre.service.ConfirmationTokenService;
import com.ftn.clinicCentre.service.EmailSenderService;
import com.ftn.clinicCentre.service.PatientService;
import com.ftn.clinicCentre.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService{
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	

	@Override
	public String register(PatientRegistrationDTO patientRegistrationDTO) {
		// TODO Auto-generated method stub
		
		try {
			String dateOfBirth = patientRegistrationDTO.getJmbg().substring(0, 2);
			String monthOfBirth = patientRegistrationDTO.getJmbg().substring(2, 4);
			String yearOfBirth = patientRegistrationDTO.getJmbg().substring(4, 7);
			if(Integer.parseInt(yearOfBirth) > 900) {
				yearOfBirth = "1" + yearOfBirth;
			}else if(Integer.parseInt(yearOfBirth) < 100) {
				yearOfBirth = "2" + yearOfBirth;
			}else {
				throw new IllegalStateException("jmbg not valid");
			}
			
			LocalDate date = LocalDate.of(Integer.parseInt(yearOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(dateOfBirth));
			if(date.isAfter(LocalDate.now())) {
				throw new IllegalStateException("jmbg not valid");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Something went wrong");	
		}
		
		boolean isValid = UserController.validate(patientRegistrationDTO.getEmail());
		if(!isValid) {
			throw new IllegalStateException("email not valid");
		}else if(patientRegistrationDTO.getFirstName().length() < 1) {
			throw new IllegalStateException("first name not valid");
		}else if(patientRegistrationDTO.getLastName().length() < 1) {
			throw new IllegalStateException("last name not valid");
		}else if(patientRegistrationDTO.getPassword().length() < 6) {
			throw new IllegalStateException("password not valid");
		}else if(patientRegistrationDTO.getAddress().length() < 1) {
			throw new IllegalStateException("address not valid");
		}else if(!patientRegistrationDTO.getPassword().equals(patientRegistrationDTO.getRepeatedPassword())) {
			throw new IllegalStateException("passwords do not match");
		}
		
		String token = patientService.signUp(patientRegistrationDTO);
		
//		String link = "https://localhost:8080/api/patients/confirm?token=" + token;
//		emailSenderService.send(patientRegistrationDTO.getEmail(), emailSenderService.registerEmail(patientRegistrationDTO.getFirstName(), link));
	
		return token;
	}

	@Transactional
	@Override
	public String confirmToken(String token) {
		// TODO Auto-generated method stub
		ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
		Patient patient = patientService.findPatientById(confirmationToken.getUser().getId());
		
		if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
		
		confirmationToken.setConfirmedAt(LocalDateTime.now());
		
		confirmationTokenService.save(confirmationToken);
		
		patient.setStatus(EUserStatus.APPROVED);
		patientService.save(patient);
		
		return "confirmed";
	}

	
	
}
