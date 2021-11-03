package com.example.DoctorOffice.service;

import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient setPatient(Patient patient){
        Patient patientToSave = null;
        if(patient.getPatientId()!= null ){
            patientToSave = patient;
        }else {
            patientToSave = new Patient(patient.getName(), patient.getLastName(),patient.getBirthDate(), patient.getPhone(), patient.getEmail(), patient.getPassword());
        }
        return patientRepository.save(patientToSave);
    }

    public List<Patient> selectAllPatients(){
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientUsingId(Integer id){
        return patientRepository.findById(id);
    }

    public String deletePatientById(Integer id){
        Optional<Patient> patientToDelete = patientRepository.findById(id);
        if(patientToDelete.isPresent()){
            Patient patientFound = patientToDelete.get();
            String patientId= String.valueOf(patientFound.getPatientId());
            patientRepository.delete(patientFound);
            return "Deleted: " + patientId;
        }
        return "Patient not found!";
    }
    public Optional findByEmail(String email){
        return patientRepository.findByEmail(email);
    }
    public Patient findByNameAndLastName(String name, String lastName){
        return patientRepository.findByNameAndLastName(name,lastName).orElse(null);
    }
    public boolean loginPatient(String email, String password){
        try {
            Optional<Patient> patient = patientRepository.findByEmail(email);
            Patient patientFound = patient.get();
            if (patientFound.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex){
            return false;
        }

    }


}
