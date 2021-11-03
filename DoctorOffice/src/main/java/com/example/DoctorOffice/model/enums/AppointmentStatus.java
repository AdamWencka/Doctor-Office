package com.example.DoctorOffice.model.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    Available("Dostępny"),
    Booked("Zarezerwowany");

    private  final String statusName;
    AppointmentStatus(String statusName){
        this.statusName = statusName;
    }
}
