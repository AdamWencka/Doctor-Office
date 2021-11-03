package com.example.DoctorOffice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name= "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;
    private String name;
    private String lastName;
    private String phone;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @Column(name = "registration_time")
    private LocalDateTime registrationDateTime = LocalDateTime.now();
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.REFRESH)
    @JsonIgnoreProperties({"doctor"})
    private List<Prescription> prescriptionList;
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"doctor"})
    private List<Appointment> appointmentsList;

    public Doctor(String name, String lastName, String phone, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

}
