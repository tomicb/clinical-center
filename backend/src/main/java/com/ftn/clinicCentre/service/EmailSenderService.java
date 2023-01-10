package com.ftn.clinicCentre.service;

public interface EmailSenderService {
	
	void send(String to, String email, String subject);
	String passwordlessLoginEmail(String name, String link);
	String registerEmail(String name, String link);
}
