package com.example.DoctorOffice.model;

import com.example.DoctorOffice.model.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
    @DateTimeFormat(pattern = "HH.mm")
    private LocalTime appointmentStartTime;
    @DateTimeFormat(pattern = "HH.mm")
    private LocalTime appointmentEndTime;
    private AppointmentStatus status = AppointmentStatus.Available;
    private String roomNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="patient_id",nullable = false)
    @JsonIgnoreProperties({"appointmentsList"})
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="doctor_id",nullable = false)
    @JsonIgnoreProperties({"appointmentsList"})
    private Doctor doctor;

    public Appointment(LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime, AppointmentStatus status, String roomNumber, Patient patient, Doctor doctor) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.status = status;
        this.roomNumber = roomNumber;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Appointment(LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime, AppointmentStatus status, String roomNumber) {
        this.appointmentDate = appointmentDate;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.status = status;
        this.roomNumber = roomNumber;
    }
}

