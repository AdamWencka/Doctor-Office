package com.example.DoctorOffice.views;


import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.service.AppointmentService;
import com.example.DoctorOffice.service.DoctorService;
import com.example.DoctorOffice.service.PatientService;
import com.example.DoctorOffice.service.PrescriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


// TODO : Prescriptions View where patient can see his disease history and prescription and doctor can create and edit those prescriptions and also find them by patient
// TODO : Appointment View (calendar if possible) where doctor can create/edit appointments in a given day also book and assign them to patients, and where patient can see them and assign himself to them
@UIScope
@SpringComponent
@Route("doctorsOffice")
public class ViewController extends VerticalLayout {
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private PrescriptionService prescriptionService;

    private boolean zalogowany = false;
    private Patient patient;
    private Doctor doctor;
    VerticalLayout vl;
    VerticalLayout vl2;
    VerticalLayout vl3;
    Grid<Doctor> doctorGrid;
    Label label;
    Button loginPatient;
    Button registerPatient;
    Button loginDoctor;
    Button createPatient;
    Button goToPrescriptions;
    Button goToAppointments;
    Button goToServiceAndPrices;
    Button returnToPreviousPanel;
    H1 h1;
    H2 h2info;
    H2 h2doctors;
    Paragraph paragraph;
    Image image;
    TextField emailField;
    TextField nameField;
    TextField lastNameField;
    TextField birthDateField;
    TextField phoneField;
    PasswordField password;
    Button loginButton;
    TextField doctorEmail;
    HorizontalLayout hl;
    HorizontalLayout hl2;
    @PostConstruct
    private void init() {
        if (!zalogowany){
            mainPagePanel();
        }
    }

    public void mainPagePanel(){
        hl = new HorizontalLayout();
        hl2 = new HorizontalLayout();
        vl = new VerticalLayout();
        vl2 = new VerticalLayout();
        vl3 = new VerticalLayout();

        h1 = new H1("Gabinet Lekarski Medic");
        if(!zalogowany) {
            loginPatient = new Button("Zaloguj jako pacjent", buttonClickEvent -> {
                removeAll();
                patientLoginPanel();
            });
            loginDoctor = new Button("Zaloguj jako lekarz", buttonClickEvent -> {
                removeAll();
                doctorLoginPanel();
            });
        } else if (zalogowany && patient != null){
            loginDoctor = new Button("Wyloguj z konta, " + patient.getEmail(), buttonClickEvent -> {
                zalogowany =false;
                if(patient !=null){
                    patient = null;
                }
                removeAll();
                mainPagePanel();
            });
            loginPatient = new  Button ("Konto pacjenta, " +patient.getEmail(),buttonClickEvent -> {
                removeAll();
                patientEditAccount();
            });

        }else if (zalogowany && doctor != null){
            loginDoctor = new Button("Wyloguj z konta, " + doctor.getEmail(), buttonClickEvent -> {
                zalogowany =false;
                if(doctor !=null){
                    doctor = null;
                }
                removeAll();
                mainPagePanel();
            });
            loginPatient = new  Button ("Konto lekarza, " +doctor.getEmail(),buttonClickEvent -> {
                removeAll();
                doctorEditAccount();
            });

        }

        goToServiceAndPrices = new Button("Usługi i cennik", buttonClickEvent -> {
           removeAll();
           servicesAndPricesPanel();
        });
        goToPrescriptions = new Button("Historia chorób i recepty",buttonClickEvent -> {
            if(!zalogowany){
                Notification.show("Zaloguj się na konto !!!");
            }else {
                removeAll();
                prescriptionsPanel();
            }
        });
        goToAppointments = new Button("Wizyty", buttonClickEvent -> {
            if(!zalogowany){
                Notification.show("Zaloguj się na konto !!!");
            }else {
                removeAll();
                appointmentsPanel();
            }
        });
        hl.add(loginPatient, loginDoctor,goToServiceAndPrices, goToPrescriptions, goToAppointments);
        image = new Image("https://lh3.googleusercontent.com/proxy/VfAQ5oMiJXsN_1QSPhOVje2-1IZEleQyoTmuvSlMcx5tO7L0bUy8NTUVk8lnQf8wvPNodBeWwERb02xoHJb6aczm6VWe8nvb-13i7VuZYIC7Uvgu_kpW8fFvybqdqBH5MRgZn0AuqKPCRabm2wDKldxWS5ie27ux","Image Doctor's Office");
        paragraph = new Paragraph();
        String html = "<br>Kompleksowa opieka medyczna w Bydgoszczy.<br>" +
                "Nasz Gabinet to ośrodek oferujący Państwu opiekę medyczną w zakresie wielu dyscyplin lekarskich.<br>" +
                "Pomagamy kompleksowo w odzyskiwaniu zdrowia, a także stosujemy programy profilaktyczne <br>" +
                " w celu zapobiegania różnym chorobom. W celu nieustannego rozszerzania naszej oferty <br>" +
                " nawiązujemy współpracę z innymi ośrodkami medycznymi, szpitalami oraz wieloma firmami <br>" +
                " i instytucjami w zarówno w kraju, jak i za granicą.";
        paragraph.getElement().setProperty("innerHTML", html);
        h2info = new H2("Informacje:");
        String html2 ="Adres: ul.Szubińska 93-95<br>"+
                "85-312 Bydgoszcz<br>"+
                " <br>"+
                "Godziny otwarcia: <br>"+
                "Pn-Czw: 7:00-20:00 <br>"+
                "Pt: 7:00-17:00 <br>"+
                "Sob-Ndz: nieczynne <br>"+
                " <br>"+
                "Telefon: (52) 213 34 70 lub (52) 213 34 50 <br>"+
                " <br>"+
                "Faks: (52) 213 34 60 <br>"+
                " <br>"+
                "Email: info@gabinet.eu";
        label = new Label();
        label.getElement().setProperty("innerHTML",html2);
        label.setWidth("600px");
        vl2.add(h2info,label);
        h2doctors = new H2("Lekarze:");
        doctorGrid = new Grid<>(Doctor.class);
        doctorGrid.setItems(doctorService.selectAllDoctors());
        doctorGrid.setColumns("name","lastName","phone","email");
        doctorGrid.getColumnByKey("name").setHeader("Imię");
        doctorGrid.getColumnByKey("lastName").setHeader("Nazwisko");
        doctorGrid.getColumnByKey("phone").setHeader("Numer telefonu");
        vl3.add(h2doctors,doctorGrid);
        doctorGrid.setWidth("1000px");
        hl2.add(vl2,vl3);
        vl.add(h1,hl,image, paragraph,hl2 );
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,image);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,paragraph);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,hl);
        add(vl);
    }

    public void patientLoginPanel(){
        vl = new VerticalLayout();
         h1 = new H1("Witaj Pacjencie");
         hl = new HorizontalLayout();
         emailField = new TextField("Email");
         password = new PasswordField("Hasło");
        loginButton = new Button("Zaloguj", buttonClickEvent -> {
            if (patientService.loginPatient(emailField.getValue(),password.getValue())){
                zalogowany = true;
                Optional patientOptional = patientService.findByEmail(emailField.getValue());
                patient = (Patient) patientOptional.get();
                removeAll();
                mainPagePanel();
            }
        });
        registerPatient = new Button("Zarejestruj się", buttonClickEvent -> {
           removeAll();
           registerPatientPanel();
        });
        hl.add(loginButton,registerPatient);
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
           removeAll();
           mainPagePanel();
        });
        vl.add(h1, emailField,password,hl, returnToPreviousPanel);
        add(vl);

    }
    public void doctorLoginPanel(){
        vl = new VerticalLayout();
        h1 = new H1("Witaj Lekarzu");
         doctorEmail = new TextField("Email");
         password = new PasswordField("Hasło");
         loginButton = new Button("Zaloguj", buttonClickEvent -> {
            if (doctorService.loginDoctor(doctorEmail.getValue(),password.getValue())){
                zalogowany = true;
                Optional doctorOptional = doctorService.findByEmail(doctorEmail.getValue());
                doctor = (Doctor) doctorOptional.get();
                removeAll();
                mainPagePanel();
            }
        });
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,doctorEmail,password,loginButton, returnToPreviousPanel);
        add(vl);
    }
    public void servicesAndPricesPanel(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2(" Usługi i ceny");
        String html = "Konsultacja alergologiczna - 180,00 zł <br>" +
                "Konsultacja alergologa dziecięcego - 180,00 zł <br>" +
                "Konsultacja chirurga dziecięcego - 190,00 zł <br>" +
                "Konsultacja chirurga naczyniowego - 200,00 zł <br>" +
                "Konsultacja dermatologiczna - 185,00 zł <br>" +
                "Konsultacja diabetologiczna - 190,00 zł <br>" +
                "Konsultacja dietetyka - 170,00 zł <br>" +
                "Konsultacja endokrynologiczna - 190,00 zł <br>" +
                "Konsultacja kardiologiczna - 200,00 zł <br>" +
                "Konsultacja laryngologiczna -180,00 zł <br>" +
                "Konsultacja okulistyczna - 180,00 zł <br>" +
                "Konsultacja ortopedyczna - 180,00 zł";
        paragraph = new Paragraph();
        paragraph.getElement().setProperty("innerHTML",html);

        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1, returnToPreviousPanel,h2info,paragraph);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        vl.setHorizontalComponentAlignment(Alignment.CENTER, returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h2info);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,paragraph);
        add(vl);
    }
    public void registerPatientPanel(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Rejestracja Pacjenta");
        nameField = new TextField("Imię Pacjenta");
        lastNameField = new TextField("Nazwisko Pacjenta");
        birthDateField = new TextField("Data urodzenia: (rrrr-MM-dd)");
        phoneField = new TextField("Numer telefonu Pacjenta");
        emailField = new TextField("Email Pacjenta");
        password = new PasswordField("Hasło");
        createPatient = new Button("Stwórz konto", buttonClickEvent -> {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate date = LocalDate.parse(birthDateField.getValue(),formatter);

           Optional patientExists = patientService.findByEmail(emailField.getValue());
           if(patientExists.isPresent()){
               Notification.show("Pacjent z takim email istnieje!!!");
           }else {
               patientService.setPatient(
                       new Patient(nameField.getValue(),
                               lastNameField.getValue(),
                               date,
                               phoneField.getValue(),
                               emailField.getValue(),
                               password.getValue()));
               removeAll();
               Notification.show("Stworzono konto pacjenta!");
               patientLoginPanel();
           }
        });
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            patientLoginPanel();
        });

        vl.add(h1,h2info, nameField, lastNameField, birthDateField,
                phoneField, emailField, password,createPatient, returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }
    public void patientEditAccount(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Edycja konta pacjenta");
        nameField = new TextField("Imię Pacjenta");
        lastNameField = new TextField("Nazwisko Pacjenta");
        birthDateField = new TextField("Data urodzenia: (rrrr-MM-dd)");
        phoneField = new TextField("Numer telefonu Pacjenta");
        emailField = new TextField("Email Pacjenta");
        password = new PasswordField("Hasło");
        nameField.setValue(patient.getName());
        lastNameField.setValue(patient.getLastName());
        birthDateField.setValue(patient.getBirthDate().toString());
        phoneField.setValue(patient.getPhone());
        emailField.setValue(patient.getEmail());
        password.setValue(patient.getPassword());
        createPatient = new Button("Edytuj konto", buttonClickEvent -> {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            LocalDate date = LocalDate.parse(birthDateField.getValue(),formatter);


            if(emailField.getValue() != patient.getEmail()){
                Optional patientExists = patientService.findByEmail(emailField.getValue());
                if(patientExists.isPresent()){
                    Notification.show("Istnieje konto pacjenta z takim email!");
                }else {
                    patient.setName(nameField.getValue());
                    patient.setLastName(lastNameField.getValue());
                    patient.setBirthDate(date);
                    patient.setPhone(phoneField.getValue());
                    patient.setEmail(emailField.getValue());
                    patient.setPassword(password.getValue());
                    patientService.setPatient(
                            patient);
                    removeAll();
                    Notification.show("Edytowano konto pacjenta!");
                    mainPagePanel();
                }
            }else {
                patient.setName(nameField.getValue());
                patient.setLastName(lastNameField.getValue());
                patient.setBirthDate(date);
                patient.setPhone(phoneField.getValue());
                patient.setEmail(emailField.getValue());
                patient.setPassword(password.getValue());
                patientService.setPatient(
                       patient);
                removeAll();
                Notification.show("Edytowano konto pacjenta!");
                mainPagePanel();
            }
        });
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,h2info, nameField, lastNameField, birthDateField, phoneField,
                emailField,password,createPatient, returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }
    public void doctorEditAccount(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Edycja konta lekarza");
        nameField = new TextField("Imię Lekarza");
        lastNameField = new TextField("Nazwisko Lekarza");
        phoneField = new TextField("Numer telefonu Lekarza");
        emailField = new TextField("Email Lekarza");
        password = new PasswordField("Hasło");
        nameField.setValue(doctor.getName());
        lastNameField.setValue(doctor.getLastName());
        phoneField.setValue(doctor.getPhone());
        emailField.setValue(doctor.getEmail());
        password.setValue(doctor.getPassword());
        createPatient = new Button("Edytuj konto", buttonClickEvent -> {

            if(emailField.getValue() != doctor.getEmail()){
                Optional doctorExists = doctorService.findByEmail(emailField.getValue());
                if(doctorExists.isPresent()){
                    Notification.show("Istnieje konto lekarza z takim email!");
                }else {
                    doctor.setName(nameField.getValue());
                    doctor.setLastName(lastNameField.getValue());
                    doctor.setPhone(phoneField.getValue());
                    doctor.setEmail(emailField.getValue());
                    doctor.setPassword(password.getValue());
                    doctorService.setDoctor(doctor);
                    removeAll();
                    Notification.show("Edytowano konto Lekarza!");
                    mainPagePanel();
                }
            }else {
                doctor.setName(nameField.getValue());
                doctor.setLastName(lastNameField.getValue());
                doctor.setPhone(phoneField.getValue());
                doctor.setEmail(emailField.getValue());
                doctor.setPassword(password.getValue());
                doctorService.setDoctor(doctor);
                removeAll();
                Notification.show("Edytowano konto Lekarza!");
                mainPagePanel();
            }
        });
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,h2info, nameField, lastNameField, phoneField,
                emailField,password,createPatient, returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }
    public void prescriptionsPanel(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Historia chorób, recepty");

        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,h2info,returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }
    public void appointmentsPanel(){
        vl = new VerticalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Wizyty");

        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,h2info,returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }

}
