package com.clinicmgmtsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinicmgmtsystem.entities.Pathalogist;
import com.clinicmgmtsystem.repositories.PathalogistRepository;

@Service
public class PathalogistService {

    @Autowired
    private PathalogistRepository pathalogistRepository;

    public Pathalogist savePathologist(Pathalogist pathologist) {
        return pathalogistRepository.save(pathologist);
    }

    public List<Pathalogist> findAllPathologists() {
        return pathalogistRepository.findAll();
    }

    public Optional<Pathalogist> findPathologistById(Long id) {
        return pathalogistRepository.findById(id);
    }

    public void deletePathologist(Long id) {
        pathalogistRepository.deleteById(id);
    }
}
