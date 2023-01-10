package com.ftn.clinicCentre.service;

import java.time.LocalDateTime;

import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.security.token.ConfirmationToken;

public interface ConfirmationTokenService {
	
	ConfirmationToken findByToken(String token);
	ConfirmationToken findByUser(User user);
	int updateConfirmedAt(String token,
            LocalDateTime confirmedAt);
	ConfirmationToken save(ConfirmationToken token);

}
