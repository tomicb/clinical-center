package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByJmbg(String jmbg);
    List<User> findUsersByStatus(EUserStatus userStatus);
    User save(User user);
}
