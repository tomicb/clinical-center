package com.ftn.clinicCentre.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ftn.clinicCentre.entity.Doctor;
import com.ftn.clinicCentre.entity.DoctorsReview;

public interface DoctorsReviewRepository extends JpaRepository<DoctorsReview, Long>{

	List<DoctorsReview> findByDoctor(Doctor doctor);
}
