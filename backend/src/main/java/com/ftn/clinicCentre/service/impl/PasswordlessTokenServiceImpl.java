package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.PasswordlessLoginRepository;
import com.ftn.clinicCentre.security.token.PasswordlessToken;
import com.ftn.clinicCentre.service.PasswordlessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordlessTokenServiceImpl implements PasswordlessTokenService {

    @Autowired
    PasswordlessLoginRepository passwordlessLoginRepository;

    @Override
    public PasswordlessToken save(PasswordlessToken token) {
        return passwordlessLoginRepository.save(token);
    }

    @Override
    public PasswordlessToken findByToken(String token) {
        return passwordlessLoginRepository.findPasswordlessTokenByToken(token).orElse(null);
    }

    @Override
    public PasswordlessToken findUnexpiredByUser(User user) {
        return passwordlessLoginRepository.findUnexpiredPasswordlessTokenByUser(user.getId()).orElse(null);
    }
}
