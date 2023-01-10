package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.ClinicCentreAdministrator;
import com.ftn.clinicCentre.entity.User;

import java.util.List;

public interface ClinicCentreAdministratorService {

    List<ClinicCentreAdministrator> findAll();
    ClinicCentreAdministrator findById(Long id);
    ClinicCentreAdministrator findByEmail(String email);
    ClinicCentreAdministrator findByJmbg(String jmbg);
    ClinicCentreAdministrator save(ClinicCentreAdministrator clinicCentreAdministrator);
    void insertUserAsClinicCentreAdministrator(User user);
}
