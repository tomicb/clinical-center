package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.Authority;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.AuthorityRepository;
import com.ftn.clinicCentre.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public List<Authority> findByName(String name) {
        return authorityRepository.findAuthoritiesByName(name);
    }

    @Override
    public List<Authority> findByUser(User user) {
        return null;
    }
}
