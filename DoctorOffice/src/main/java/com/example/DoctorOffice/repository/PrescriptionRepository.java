package com.example.DoctorOffice.repository;



import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.Prescription;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByDoctor(Doctor doctor);
    List<Prescription> findByPatient(Patient patient);
    Optional<Prescription> findByDisease(String disease);
    Optional<Prescription> findByMedicine(String medicine);
    @Query(nativeQuery = true, value = "SELECT * FROM prescriptions ORDER BY prescription_id DESC")
    List<Prescription> findAllByIdDesc();
}
