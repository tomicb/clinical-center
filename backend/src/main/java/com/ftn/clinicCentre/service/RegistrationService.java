package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.dto.PatientRegistrationDTO;

public interface RegistrationService {
	
	String register(PatientRegistrationDTO patientRegistrationDTO);
	String confirmToken(String token);

}
