package com.clinicmgmtsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicmgmtsystem.entities.Doctor;
import com.clinicmgmtsystem.repositories.DoctorRepository;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public Optional<Doctor> findDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    public Optional<Doctor> findDoctorByUsername(String username){
    	return doctorRepository.findByUsername(username);
    }
}
