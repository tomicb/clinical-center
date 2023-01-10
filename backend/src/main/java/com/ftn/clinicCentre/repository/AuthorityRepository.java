package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    List<Authority> findAuthoritiesByName(String name);
}
