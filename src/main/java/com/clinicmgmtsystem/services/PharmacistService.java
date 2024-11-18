package com.clinicmgmtsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicmgmtsystem.entities.Pharmacist;
import com.clinicmgmtsystem.repositories.PharmacistRepository;

@Service
public class PharmacistService {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    public Pharmacist savePharmacist(Pharmacist pharmacist) {
        return pharmacistRepository.save(pharmacist);
    }

    public List<Pharmacist> findAllPharmacists() {
        return pharmacistRepository.findAll();
    }

    public Optional<Pharmacist> findPharmacistById(Long id) {
        return pharmacistRepository.findById(id);
    }

    public void deletePharmacist(Long id) {
        pharmacistRepository.deleteById(id);
    }
}
