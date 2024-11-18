package com.clinicmgmtsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Test;


@Repository
public interface TestRepository extends JpaRepository<Test, Long>{

}
