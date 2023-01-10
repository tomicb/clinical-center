package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.clinicCentre.entity.ClinicAdministrator;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ClinicAdministratorRepository extends JpaRepository<ClinicAdministrator, Long>{
    String INSERT_USER_AS_CA = "INSERT INTO clinic_administrator(id, clinic_id) VALUES( :userId , :clinicId )";

    List<ClinicAdministrator> findClinicAdministratorByClinic(Clinic clinic);
    List<ClinicAdministrator> findClinicAdministratorByClinicNot(Clinic clinic);
    ClinicAdministrator findClinicAdministratorByEmail(String email);
    ClinicAdministrator findClinicAdministratorByJmbg(String jmbg);

    @Transactional
    @Modifying
    @Query(value = INSERT_USER_AS_CA, nativeQuery = true)
    void insertUserAsClinicAdministrator(Long userId, Long clinicId);
}
