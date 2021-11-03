package com.example.DoctorOffice.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "prescriptions")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;
    private String disease;
    private String medicine;
    private String dose;
    private String route;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private String remarks;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="patient_id",nullable = false)
    @JsonIgnoreProperties({"prescriptionList"})
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="doctor_id",nullable = false)
    @JsonIgnoreProperties({"prescriptionList"})
    private Doctor doctor;

    public Prescription(String disease, String medicine, String dose, String route, String frequency, LocalDate startDate, LocalDate endDate, String remarks, Patient patient, Doctor doctor) {
        this.disease = disease;
        this.medicine = medicine;
        this.dose = dose;
        this.route = route;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remarks = remarks;
        this.patient = patient;
        this.doctor = doctor;
    }

    public Prescription(String disease, String medicine, String dose, String route, String frequency, LocalDate startDate, LocalDate endDate, String remarks) {
        this.disease = disease;
        this.medicine = medicine;
        this.dose = dose;
        this.route = route;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remarks = remarks;
    }

}
