package com.example.DoctorOffice.repository;

import com.example.DoctorOffice.model.Appointment;
import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
 List<Appointment> findByDoctor(Doctor doctor);
 List<Appointment> findByPatient(Patient patient);
 @Query(nativeQuery = true, value = "SELECT * FROM appointments  WHERE doctor_id =:doctorId AND appointment_date =:appointmentDate")
 List<Appointment> findByDoctorAndDate(@Param("doctorId") Integer doctorId, @Param("appointmentDate") LocalDate appointmentDate);
 @Query(nativeQuery = true, value = "SELECT * FROM appointments  WHERE patient_id =:patientId AND appointment_date =:appointmentDate")
 List<Appointment> findByPatientAndDate(@Param("patientId") Integer patientId, @Param("appointmentDate") LocalDate appointmentDate);
 @Query(nativeQuery = true, value = "SELECT * FROM appointments ORDER BY appointment_date DESC")
 List<Appointment> findAllByDateDesc();

}
