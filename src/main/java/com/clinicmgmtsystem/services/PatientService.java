package com.clinicmgmtsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicmgmtsystem.entities.Patient;
import com.clinicmgmtsystem.repositories.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }
    public Optional<Patient> findByUsername(String username) {
        return patientRepository.findByUsername(username);
    }
   

    public Optional<Patient> findPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
