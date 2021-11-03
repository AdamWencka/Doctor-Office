package com.example.DoctorOffice.service;

import com.example.DoctorOffice.model.Appointment;
import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.Prescription;
import com.example.DoctorOffice.model.enums.AppointmentStatus;
import com.example.DoctorOffice.repository.AppointmentRepository;
import com.example.DoctorOffice.repository.DoctorRepository;
import com.example.DoctorOffice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    public AppointmentRepository appointmentRepository;
    @Autowired
    public DoctorRepository doctorRepository;
    @Autowired
    public PatientRepository patientRepository;

    public Appointment setAppointment(Appointment appointment, Integer doctorId, Integer patientId){
        Optional<Doctor> assignedDoctor = doctorRepository.findById(doctorId);
        Optional<Patient> assignedPatient = patientRepository.findById(patientId);
        Doctor doctorFound = null;
        Patient patientFound = null;
        if(assignedDoctor.isPresent() && assignedPatient.isPresent()){
             doctorFound = assignedDoctor.get();
             patientFound = assignedPatient.get();
        }
        Appointment appointmentToSave = null;
        if(appointment.getAppointmentId()!= null ){
            appointmentToSave = appointment;
        }else {
            appointmentToSave = new Appointment(appointment.getAppointmentDate(), appointment.getAppointmentStartTime(),appointment.getAppointmentEndTime(), appointment.getStatus(), appointment.getRoomNumber(),patientFound ,doctorFound);
        }
        return appointmentRepository.save(appointmentToSave);
    }

    public List<Appointment> selectAllAppointments(){
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findAppointmentUsingId(Long id){
        return appointmentRepository.findById(id);
    }

    public String deleteAppointmentById(Long id){
        Optional<Appointment> appointmentToDelete = appointmentRepository.findById(id);
        if(appointmentToDelete.isPresent()){
            Appointment appointmentFound = appointmentToDelete.get();
            String appointmentId= String.valueOf(appointmentFound.getAppointmentId());
            appointmentRepository.delete(appointmentFound);
            return "Deleted: " + appointmentId;
        }
        return "Appointment not found!";

    }
    public Optional<Appointment> findByDoctor(Integer doctorId)
    {
        Optional<Doctor> assignedDoctor = doctorRepository.findById(doctorId);
        if(assignedDoctor.isPresent()){
            Doctor doctor = assignedDoctor.get();
            return appointmentRepository.findByDoctor(doctor);
        }
       return null;
    }
    public Optional<Appointment> findByPatient(Integer patientId){
        Optional<Patient> assignedPatient = patientRepository.findById(patientId);
        if(assignedPatient.isPresent()){
            Patient patient = assignedPatient.get();
            return appointmentRepository.findByPatient(patient);
        }
        return null;
    }
    public Optional<Appointment> findByStatus(AppointmentStatus appointmentStatus){
        return appointmentRepository.findByStatus(appointmentStatus);
    }

    public List<Appointment> findAllByDateDesc(){
        return  appointmentRepository.findAllByDateDesc();
    }

    public String changeAppointmentStatus(Long id, AppointmentStatus appointmentStatus){
        Optional<Appointment> appointmentStatusToChange = appointmentRepository.findById(id);
        if (appointmentStatusToChange.isPresent()){
            Appointment appointmentFound = appointmentStatusToChange.get();
            if(appointmentStatus!= appointmentFound.getStatus()){
                appointmentFound.setStatus(appointmentStatus);
                appointmentRepository.save(appointmentFound);
                return  "Changed status to: " + appointmentFound.getStatus();
            }else {
                return  "Task has already that status";
            }
        }
        return "Status not changed";
    }
}
