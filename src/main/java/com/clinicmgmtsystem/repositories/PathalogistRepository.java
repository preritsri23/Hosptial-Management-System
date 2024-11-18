package com.clinicmgmtsystem.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Pathalogist;


@Repository
public interface PathalogistRepository extends JpaRepository<Pathalogist, Long> {

Optional<Pathalogist> findByUsername(String username); // Query method
}