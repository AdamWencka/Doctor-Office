package com.example.DoctorOffice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;
    private String name;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String phone;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    @Column(name = "registration_time")
    private LocalDateTime registrationDateTime = LocalDateTime.now();
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"patient"})
    private List<Prescription> prescriptionList;
    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"doctor"})
    private List<Appointment> appointmentsList;

    public Patient(String name, String lastName, LocalDate birthDate, String phone, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }


}
