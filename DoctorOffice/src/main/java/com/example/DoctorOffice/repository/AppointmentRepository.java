package com.example.DoctorOffice.repository;

import com.example.DoctorOffice.model.Appointment;
import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
 Optional<Appointment> findByDoctor(Doctor doctor);
 Optional<Appointment> findByPatient(Patient patient);
 Optional<Appointment> findByStatus(AppointmentStatus appointmentStatus);
 @Query(nativeQuery = true, value = "SELECT * FROM appointments ORDER BY appointment_date DESC")
 List<Appointment> findAllByDateDesc();
}
