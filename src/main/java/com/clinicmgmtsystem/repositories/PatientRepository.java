package com.clinicmgmtsystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	Optional<Patient> findByUsername(String username); // Query method
}

