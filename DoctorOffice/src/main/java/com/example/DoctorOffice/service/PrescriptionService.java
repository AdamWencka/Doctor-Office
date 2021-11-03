package com.example.DoctorOffice.service;

import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.Prescription;
import com.example.DoctorOffice.repository.DoctorRepository;
import com.example.DoctorOffice.repository.PatientRepository;
import com.example.DoctorOffice.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    @Autowired
    public PrescriptionRepository prescriptionRepository;
    @Autowired
    public DoctorRepository doctorRepository;
    @Autowired
    public PatientRepository patientRepository;

    public Prescription setPrescription(Prescription prescription, Integer doctorId, Integer patientId){
        Optional<Doctor> assignedDoctor = doctorRepository.findById(doctorId);
        Optional<Patient> assignedPatient = patientRepository.findById(patientId);
        Doctor doctorFound = null;
        Patient patientFound = null;
        if(assignedDoctor.isPresent() && assignedPatient.isPresent()){
            doctorFound = assignedDoctor.get();
            patientFound = assignedPatient.get();
        }
        Prescription prescriptionToSave = null;
        if(prescription.getPrescriptionId()!= null ){
            prescriptionToSave = prescription;
        }else {
            prescriptionToSave = new Prescription(prescription.getDisease(), prescription.getMedicine(),prescription.getDose(), prescription.getRoute(), prescription.getFrequency(), prescription.getStartDate(),prescription.getEndDate(), prescription.getRemarks(), patientFound,doctorFound);
        }
        return prescriptionRepository.save(prescriptionToSave);
    }

    public List<Prescription> selectAllPrescriptions(){
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> findPrescriptionUsingId(Long id){
        return prescriptionRepository.findById(id);
    }

    public String deletePrescriptionById(Long id){
        Optional<Prescription> prescriptionToDelete = prescriptionRepository.findById(id);
        if(prescriptionToDelete.isPresent()){
            Prescription prescriptionFound = prescriptionToDelete.get();
            String prescriptionId= String.valueOf(prescriptionFound.getPrescriptionId());
            prescriptionRepository.delete(prescriptionFound);
            return "Deleted: " + prescriptionId;
        }
        return "Prescription not found!";
    }
    public Optional<Prescription> findByDoctor(Integer doctorId){
        Optional<Doctor> assignedDoctor = doctorRepository.findById(doctorId);
        if(assignedDoctor.isPresent()){
            Doctor doctor = assignedDoctor.get();
            return prescriptionRepository.findByDoctor(doctor);
        }
        return null;

    }
    public Optional<Prescription> findByPatient(Integer patientId){
        Optional<Patient> assignedPatient = patientRepository.findById(patientId);
        if(assignedPatient.isPresent()){
            Patient patient = assignedPatient.get();
            return prescriptionRepository.findByPatient(patient);
        }
        return null;

    }
    public Optional<Prescription> findByDisease(String disease){
        return prescriptionRepository.findByDisease(disease);
    }
    public Optional<Prescription> findByMedicine(String medicine){
        return prescriptionRepository.findByMedicine(medicine);
    }
    public List<Prescription> findAllByIdDesc(){
      return  prescriptionRepository.findAllByIdDesc();
    }
}
