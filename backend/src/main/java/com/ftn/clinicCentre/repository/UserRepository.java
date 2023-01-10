package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.EUserStatus;
import com.ftn.clinicCentre.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
    List<User> findUsersByStatus(EUserStatus status);
    User findUserByJmbg(String jmbg);
}
