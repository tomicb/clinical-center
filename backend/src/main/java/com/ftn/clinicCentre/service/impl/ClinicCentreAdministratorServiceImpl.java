package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.ClinicCentreAdministrator;
import com.ftn.clinicCentre.entity.EUserRole;
import com.ftn.clinicCentre.entity.User;
import com.ftn.clinicCentre.repository.ClinicCentreAdministratorRepository;
import com.ftn.clinicCentre.service.ClinicCentreAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicCentreAdministratorServiceImpl implements ClinicCentreAdministratorService {

    @Autowired
    private ClinicCentreAdministratorRepository clinicCentreAdministratorRepository;

    @Override
    public List<ClinicCentreAdministrator> findAll() {
        return clinicCentreAdministratorRepository.findAll();
    }

    @Override
    public ClinicCentreAdministrator findById(Long id) {
        return clinicCentreAdministratorRepository.findById(id).orElse(null);
    }

    @Override
    public ClinicCentreAdministrator findByEmail(String email) {
        return clinicCentreAdministratorRepository.findClinicCentreAdministratorByEmail(email);
    }

    @Override
    public ClinicCentreAdministrator findByJmbg(String jmbg) {
        return clinicCentreAdministratorRepository.findClinicCentreAdministratorByJmbg(jmbg);
    }

    @Override
    public ClinicCentreAdministrator save(ClinicCentreAdministrator clinicCentreAdministrator) {
        return clinicCentreAdministratorRepository.save(clinicCentreAdministrator);
    }

    @Override
    public void insertUserAsClinicCentreAdministrator(User user) {
        clinicCentreAdministratorRepository.insertUserAsClinicCentreAdministrator(user.getId());
    }
}
