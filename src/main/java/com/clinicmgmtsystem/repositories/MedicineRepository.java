package com.clinicmgmtsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinicmgmtsystem.entities.Medicine;

@Repository
public interface MedicineRepository  extends JpaRepository<Medicine, Long>{
    
}
