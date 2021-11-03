package com.example.DoctorOffice.service;

import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor setDoctor(Doctor doctor){
        Doctor doctorToSave = null;
        if(doctor.getDoctorId() != null ){
            doctorToSave = doctor;
        }else {
            doctorToSave = new Doctor(doctor.getName(), doctor.getLastName(), doctor.getPhone(), doctor.getEmail(), doctor.getPassword());
        }
        return doctorRepository.save(doctorToSave);
    }

    public List<Doctor> selectAllDoctors(){
        return doctorRepository.findAll();
    }

    public Optional<Doctor> findDoctorUsingId(Integer id){
        return doctorRepository.findById(id);
    }

    public String deleteDoctorById(Integer id){
        Optional<Doctor> doctorToDelete = doctorRepository.findById(id);
        if(doctorToDelete.isPresent()){
            Doctor doctorFound = doctorToDelete.get();
            String doctorId= String.valueOf(doctorFound.getDoctorId());
            doctorRepository.delete(doctorFound);
            return "Deleted: " + doctorId;
        }
        return "Doctor not found!";
    }
    public Optional findByEmail(String email){
        return doctorRepository.findByEmail(email);
    }
    public Doctor findByNameAndLastName(String name, String lastName){
        return doctorRepository.findByNameAndLastName(name,lastName).orElse(null);
    }
    public boolean loginDoctor(String email, String password){
        try {
            Optional<Doctor> doctor = doctorRepository.findByEmail(email);
            Doctor doctorFound = doctor.get();
            if (doctorFound.getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex){
            return false;
        }

    }
}
