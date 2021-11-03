package com.example.DoctorOffice.controller;

import com.example.DoctorOffice.model.Appointment;
import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.Prescription;
import com.example.DoctorOffice.model.enums.AppointmentStatus;
import com.example.DoctorOffice.service.AppointmentService;
import com.example.DoctorOffice.service.DoctorService;
import com.example.DoctorOffice.service.PatientService;
import com.example.DoctorOffice.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class ServiceController {

    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private PrescriptionService prescriptionService;
    @Autowired
    public ServiceController(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService, PrescriptionService prescriptionService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
    }

    // Patient entity services (CRUD & others)
    @PostMapping("/patients/patientCreate")
    public Patient createPatient(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("password") String password)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(birthDate,formatter);
        return patientService.setPatient(new Patient(name,lastName,date,phone,email,password));
    }
    @GetMapping("/patients")
    public List<Patient> getAllPatients(){
        return patientService.selectAllPatients();
    }
    @GetMapping("/patient/id={id}")
    public Patient findPatientById(@PathVariable("id")Integer id){
        Optional patientFound = patientService.findPatientUsingId(id);
        if(!patientFound.isPresent()){
            return null;
        }
        return (Patient) patientFound.get();
    }
    @GetMapping("/patient/email={email}")
    public Patient findPatientByEmail(@PathVariable("email") String email){
        Optional patientFound = patientService.findByEmail(email);
        if(!patientFound.isPresent()){
            return null;
        }
        return (Patient) patientFound.get();
    }
    @GetMapping("/patient/name={name}&lastName={lastName}")
    public Patient findPatientByNameAndLastName(@PathVariable("name")String name, @PathVariable("lastName")String lastName){
        return  patientService.findByNameAndLastName(name,lastName);
    }

    @DeleteMapping("/patient/delete")
    public String deletePatient(@RequestParam("id")Integer id){
        return patientService.deletePatientById(id);
    }

    // Doctor entity services (CRUD & others)

    @PostMapping("/doctors/doctorCreate")
    public Doctor createDoctor(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("password") String password)
    {
        return doctorService.setDoctor(new Doctor(name,lastName,phone,email,password));
    }
    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.selectAllDoctors();
    }
    @GetMapping("/doctor/id={id}")
    public Doctor findDoctorById(@PathVariable("id")Integer id){
        Optional doctorFound = doctorService.findDoctorUsingId(id);
        if(!doctorFound.isPresent()){
            return null;
        }
        return (Doctor) doctorFound.get();
    }
    @GetMapping("/doctor/email={email}")
    public Doctor findDoctorByEmail(@PathVariable("email") String email){
        Optional doctorFound = doctorService.findByEmail(email);
        if(!doctorFound.isPresent()){
            return null;
        }
        return (Doctor) doctorFound.get();
    }
    @GetMapping("/doctor/name={name}&lastName={lastName}")
    public Doctor findDoctorByNameAndLastName(@PathVariable("name")String name, @PathVariable("lastName")String lastName){
        return  doctorService.findByNameAndLastName(name,lastName);
    }

    @DeleteMapping("/doctor/delete")
    public String deleteDoctor(@RequestParam("id")Integer id){
        return doctorService.deleteDoctorById(id);
    }


    @PostMapping("/appointments/appointmentCreate")
    public Appointment createAppointment(
            @RequestParam("appointmentDate") String appointmentDate,
            @RequestParam("appointmentStartTime") String appointmentStartTime,
            @RequestParam("appointmentEndTime") String appointmentEndTime,
            @RequestParam("status") AppointmentStatus status,
            @RequestParam("roomNumber") String roomNumber,
            @RequestParam("doctorId") Integer doctorId,
            @RequestParam("patientId") Integer patientId)
    {
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE;
        LocalDate date = LocalDate.parse(appointmentDate,formatter1);
        DateTimeFormatter formatter2 =  DateTimeFormatter.ofPattern("HH.mm");
        LocalTime startTime = LocalTime.parse(appointmentStartTime,formatter2);
        LocalTime endTime = LocalTime.parse(appointmentEndTime,formatter2);
        return appointmentService.setAppointment(new Appointment(date,startTime,endTime,status,roomNumber),doctorId,patientId);
    }
    @GetMapping("/appointments")
    public List<Appointment> getAllAppointments(){
        return appointmentService.selectAllAppointments();
    }

    @GetMapping("/appointment/id={id}")
    public Appointment findAppointmentById(@PathVariable("id")Long id){
        Optional appointmentFound = appointmentService.findAppointmentUsingId(id);
        if(!appointmentFound.isPresent()){
            return null;
        }
        return (Appointment) appointmentFound.get();
    }

    @DeleteMapping("/appointment/delete")
    public String deleteAppointment(@RequestParam("id")Long id){
        return appointmentService.deleteAppointmentById(id);
    }

    @GetMapping("/appointment/doctorId={doctorId}")
    public Appointment findAppointmentByDoctor(@PathVariable("doctorId")Integer doctorId){
        Optional appointmentFound = appointmentService.findByDoctor(doctorId);
        if(!appointmentFound.isPresent()){
            return null;
        }
        return (Appointment) appointmentFound.get();
    }
    @GetMapping("/appointment/patientId={patientId}")
    public Appointment findAppointmentByPatient(@PathVariable("patientId")Integer patientId){
        Optional appointmentFound = appointmentService.findByPatient(patientId);
        if(!appointmentFound.isPresent()){
            return null;
        }
        return (Appointment) appointmentFound.get();
    }
    @GetMapping("/appointment/status={status}")
    public  Optional<Appointment> findAppointmentByStatus(@PathVariable("status")AppointmentStatus appointmentStatus){
        return appointmentService.findByStatus(appointmentStatus);
    }
    @GetMapping("/appointmentsDateDesc")
    public List<Appointment> getAllAppointmentsDateDesc(){
        return appointmentService.findAllByDateDesc();
    }
    @PutMapping("/appointment/status")
    public  String changeAppointmentStatus(
            @RequestParam("id") Long id,
            @RequestParam("status") AppointmentStatus status){
        return appointmentService.changeAppointmentStatus(id,status);
    }
    @PostMapping("/prescriptions/prescriptionCreate")
    public Prescription createPrescription(
            @RequestParam("disease") String disease,
            @RequestParam("medicine") String medicine,
            @RequestParam("dose") String dose,
            @RequestParam("route") String route,
            @RequestParam("frequency") String frequency,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("remarks") String remarks,
            @RequestParam("doctorId") Integer doctorId,
            @RequestParam("patientId") Integer patientId)
    {
        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE;
        LocalDate date1 = LocalDate.parse(startDate,formatter1);
        LocalDate date2 = LocalDate.parse(endDate,formatter1);
        return prescriptionService.setPrescription(new Prescription(disease,medicine,dose,route,frequency,date1,date2,remarks),doctorId,patientId);
    }
    @GetMapping("/prescriptions")
    public List<Prescription> getAllPrescriptions(){
        return prescriptionService.selectAllPrescriptions();
    }
    @GetMapping("/prescription/id={id}")
    public Prescription findPrescriptionById(@PathVariable("id")Long id){
        Optional prescriptionFound = prescriptionService.findPrescriptionUsingId(id);
        if(!prescriptionFound.isPresent()){
            return null;
        }
        return (Prescription) prescriptionFound.get();
    }
    @DeleteMapping("/prescription/delete")
    public String deletePrescription(@RequestParam("id")Long id){
        return prescriptionService.deletePrescriptionById(id);
    }
    @GetMapping("/prescription/doctorId={doctorId}")
    public Prescription findPrescriptionByDoctor(@PathVariable("doctorId")Integer doctorId){
        Optional prescriptionFound = prescriptionService.findByDoctor(doctorId);
        if(!prescriptionFound.isPresent()){
            return null;
        }
        return (Prescription) prescriptionFound.get();
    }
    @GetMapping("/prescription/patientId={patientId}")
    public Prescription findPrescriptionByPatient(@PathVariable("patientId")Integer patientId){
        Optional prescriptionFound = prescriptionService.findByPatient(patientId);
        if(!prescriptionFound.isPresent()){
            return null;
        }
        return (Prescription) prescriptionFound.get();
    }
    @GetMapping("/prescription/disease={disease}")
    public Prescription findPrescriptionByDisease(@PathVariable("disease") String disease){
        Optional prescriptionFound = prescriptionService.findByDisease(disease);
        if(!prescriptionFound.isPresent()){
            return null;
        }
        return (Prescription) prescriptionFound.get();
    }
    @GetMapping("/prescription/medicine={medicine}")
    public Prescription findPrescriptionByMedicine(@PathVariable("medicine") String medicine){
        Optional prescriptionFound = prescriptionService.findByMedicine(medicine);
        if(!prescriptionFound.isPresent()){
            return null;
        }
        return (Prescription) prescriptionFound.get();
    }
    @GetMapping("/prescriptionsIdDesc")
    public List<Prescription> getAllPrescriptionsIdDesc(){
        return prescriptionService.findAllByIdDesc();
    }

}
