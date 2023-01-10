package com.ftn.clinicCentre.service.impl;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.ConfirmationTokenRepository;
import com.ftn.clinicCentre.security.token.ConfirmationToken;
import com.ftn.clinicCentre.service.ConfirmationTokenService;

@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
	
	@Autowired
	private ConfirmationTokenRepository repo;

	@Override
	public ConfirmationToken findByToken(String token) {
		// TODO Auto-generated method stub
		return repo.findByToken(token);
	}

	@Override
	public int updateConfirmedAt(String token, LocalDateTime confirmedAt) {
		// TODO Auto-generated method stub
		return repo.updateConfirmedAt(token, confirmedAt);
		
	}

	@Override
	public ConfirmationToken save(ConfirmationToken token) {
		// TODO Auto-generated method stub
		return repo.save(token);
		
	}

	@Override
	public ConfirmationToken findByUser(User user) {
		// TODO Auto-generated method stub
		return repo.findByUser(user);
	}

	

}
