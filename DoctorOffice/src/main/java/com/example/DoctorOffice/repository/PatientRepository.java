package com.example.DoctorOffice.repository;

import com.example.DoctorOffice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByNameAndLastName(String name, String lastName);
}
