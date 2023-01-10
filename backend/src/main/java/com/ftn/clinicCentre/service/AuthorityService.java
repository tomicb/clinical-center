package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.Authority;
import com.ftn.clinicCentre.entity.User;

import java.util.List;

public interface AuthorityService {

    Authority save(Authority authority);
    List<Authority> findByName(String name);
    List<Authority> findByUser(User user);
}
