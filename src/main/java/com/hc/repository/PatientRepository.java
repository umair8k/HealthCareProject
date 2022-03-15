package com.hc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hc.model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    Optional<Patient> findByUserName(String username);
}
