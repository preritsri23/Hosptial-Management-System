package com.clinicmgmtsystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Patient;
import com.clinicmgmtsystem.entities.Pharmacist;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
	Optional<Pharmacist> findByUsername(String username); // Query method
}

