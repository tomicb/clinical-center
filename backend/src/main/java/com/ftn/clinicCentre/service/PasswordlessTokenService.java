package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.security.token.PasswordlessToken;

public interface PasswordlessTokenService {

    PasswordlessToken save(PasswordlessToken token);
    PasswordlessToken findByToken(String token);
    PasswordlessToken findUnexpiredByUser(User user);
}
