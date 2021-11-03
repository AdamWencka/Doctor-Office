package com.example.DoctorOffice.repository;

import com.example.DoctorOffice.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByNameAndLastName(String name, String lastName);
}
