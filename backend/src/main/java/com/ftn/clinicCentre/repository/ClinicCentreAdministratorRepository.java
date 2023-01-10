package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.ClinicCentreAdministrator;
import com.ftn.clinicCentre.entity.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ClinicCentreAdministratorRepository  extends JpaRepository<ClinicCentreAdministrator, Long> {
    String INSERT_USER_AS_CCA = "INSERT INTO clinic_centre_administrator(id) VALUES( :userId )";

    ClinicCentreAdministrator findClinicCentreAdministratorByEmail(String email);
    ClinicCentreAdministrator findClinicCentreAdministratorByJmbg(String jmbg);

    @Transactional
    @Modifying
    @Query(value = INSERT_USER_AS_CCA, nativeQuery = true)
    void insertUserAsClinicCentreAdministrator(Long userId);
}
