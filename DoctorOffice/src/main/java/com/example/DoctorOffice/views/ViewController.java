package com.example.DoctorOffice.views;


import com.example.DoctorOffice.model.Appointment;
import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.model.Prescription;
import com.example.DoctorOffice.service.AppointmentService;
import com.example.DoctorOffice.service.DoctorService;
import com.example.DoctorOffice.service.PatientService;
import com.example.DoctorOffice.service.PrescriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;


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
    Grid<Prescription> prescriptionGrid;
    Grid<Appointment> appointmentGrid;
    Label label;
    Label appointmentDetails;
    Label appointmentInfo;
    Button loginPatient;
    Button registerPatient;
    Button loginDoctor;
    Button createPatient;
    Button goToPrescriptions;
    Button goToAppointments;
    Button goToServiceAndPrices;
    Button returnToPreviousPanel;
    Button searchAllPrescriptions;
    Button searchPrescriptionsUsingPatient;
    Button searchAppointments;
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


    //Prescriptions
    TextField prescriptionId;
    TextField disease;
    TextField medicine;
    TextField dose;
    TextField route;
    TextField frequency;
    TextField startDate;
    TextField endDate;
    TextArea remarks;
    TextField nameField2;
    TextField lastNameField2;

    Appointment selectedAppointment;
    Appointment appointmentToEdit;

    DatePicker datePicker;
    TimePicker timePicker;
    TextField roomNumberField;
    Button createAppointment;
    Button editAppointment;
    Button deleteAppointment;


    Button loginButton;
    boolean formFilled = false;
    Button createPrescription;
    Button editPrescription;
    TextField doctorEmail;
    HorizontalLayout hl;
    HorizontalLayout hl2;
    HorizontalLayout hl3;
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
        image = new Image("https://biuromarket.com.pl/media/magefan_blog/7fed6fe52324df59c3e8bafd12d7cdbe.jpg","Image Doctor's Office");
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
        vl2 = new VerticalLayout();
        vl3 = new VerticalLayout();
        hl = new HorizontalLayout();
        hl2 = new HorizontalLayout();

        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Historia chorób, recepty");
        nameField = new TextField("Imię pacjenta");
        lastNameField = new TextField("Nazwisko pacjenta");
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        if(doctor !=null && zalogowany ){
            searchAllPrescriptions = new Button("Wyszukaj wszyskie opisy chorób i recepty wystawione przez ciebie", buttonClickEvent -> {
               prescriptionGrid.setItems(prescriptionService.findByDoctor(doctor.getDoctorId()));
            });
            searchPrescriptionsUsingPatient = new Button("Wyszukaj wszystkie opisy chorób i recepty dla danego pacjenta", buttonClickEvent -> {
                Patient patientAssigned = patientService.findByNameAndLastName(nameField.getValue(),lastNameField.getValue());
                if(patientAssigned !=null)
                prescriptionGrid.setItems(prescriptionService.findByPatient(patientAssigned.getPatientId()));
                else Notification.show("Nie znaleziono takiego pacjenta");
            });
            prescriptionGrid = new Grid<>(Prescription.class);
            prescriptionGrid.setColumns("prescriptionId", "disease", "medicine", "dose","route", "frequency","startDate","endDate","remarks");
            prescriptionGrid.getColumnByKey("prescriptionId").setHeader("Id:").setWidth("50px");
            prescriptionGrid.getColumnByKey("disease").setHeader("Nazwa choroby:");
            prescriptionGrid.getColumnByKey("medicine").setHeader("Nazwa lekarstwa:");
            prescriptionGrid.getColumnByKey("dose").setHeader("Dawka:");
            prescriptionGrid.getColumnByKey("route").setHeader("Droga podania lekarstwa:");
            prescriptionGrid.getColumnByKey("frequency").setHeader("Częstotliwość:");
            prescriptionGrid.getColumnByKey("startDate").setHeader("Dzień rozpoczęcia przyjmowania leku:");
            prescriptionGrid.getColumnByKey("endDate").setHeader("Dzień końca przyjmowania leku:");
            prescriptionGrid.getColumnByKey("remarks").setHeader("Uwagi, zalecenia:");
            prescriptionGrid.setItems(prescriptionService.findByDoctor(doctor.getDoctorId()));
            prescriptionGrid.setThemeName("wrap-cell-content");

            hl.add(nameField,lastNameField);

            label = new Label("Dodaj lub zmodyfikuj historię choroby i receptę:");
            prescriptionId = new TextField("Id histori choroby (Wypełnij tylko, jeśli chcesz edytować)");
            nameField2= new TextField("Imię pacjenta:");
            lastNameField2 = new TextField("Nazwisko pacjenta:");
            disease = new TextField("Choroba:");
            medicine = new TextField("Lekarstwo:");
            dose = new TextField("Dawka:");
            route = new TextField("Droga podania leku:");
            frequency = new TextField("Częstotliwość podawania leku:");
            startDate = new TextField("Data rozpoczęcia przyjmowania leku (rrrr-MM-dd):");
            endDate = new TextField("Data zakończenia przyjmowania leku (rrrr-MM-dd):");
            remarks = new TextArea("Opis, uwagi, opinia lekarska:");
            prescriptionId.setWidth("200px");
            nameField2.setWidth("150px");
            lastNameField2.setWidth("150px");
            disease.setWidth("150px");
            medicine.setWidth("300px");
            dose.setWidth("150px");
            route.setWidth("200px");
            frequency.setWidth("300px");
            startDate.setWidth("350px");
            endDate.setWidth("350px");
            remarks.setWidth("800px");
            remarks.setHeight("300px");

            createPrescription = new Button("Utwórz rekord w historii chorób i recept", buttonClickEvent -> {
                Patient patientPrescription = patientService.findByNameAndLastName(nameField2.getValue(),lastNameField2.getValue());
                if(patientPrescription == null)
                    Notification.show("Nie istnieje taki pacjent");
                else {
                    DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE;
                    LocalDate date1 = LocalDate.parse(startDate.getValue(),formatter1);
                    LocalDate date2 = LocalDate.parse(endDate.getValue(),formatter1);
                    prescriptionService.setPrescription(new Prescription(
                            disease.getValue(),
                            medicine.getValue(),
                            dose.getValue(),
                            route.getValue(),
                            frequency.getValue(),
                            date1,
                            date2,
                            remarks.getValue()
                    ), doctor.getDoctorId(),patientPrescription.getPatientId());
                    removeAll();
                    prescriptionsPanel();
                }
            });
            Button fillEditForm = new Button("Uzupełnij formularz istniejącym rekordem", buttonClickEvent -> {
                Long id = Long.parseLong(prescriptionId.getValue());
                Optional<Prescription> prescriptionExists = prescriptionService.findPrescriptionUsingId(id);
                Prescription prescriptionFound = null;
                if(prescriptionExists.isPresent()){
                    prescriptionFound = prescriptionExists.get();
                    disease.setValue(prescriptionFound.getDisease());
                    medicine.setValue(prescriptionFound.getMedicine());
                    dose.setValue(prescriptionFound.getDose());
                    route.setValue(prescriptionFound.getRoute());
                    frequency.setValue(prescriptionFound.getFrequency());
                    startDate.setValue(prescriptionFound.getStartDate().toString());
                    endDate.setValue(prescriptionFound.getEndDate().toString());
                    remarks.setValue(prescriptionFound.getRemarks());
                    nameField2.setValue(prescriptionFound.getPatient().getName());
                    lastNameField2.setValue(prescriptionFound.getPatient().getLastName());
                    formFilled = true;
                    editPrescription.setEnabled(true);
                } else {
                    Notification.show("Nie ma takiego rekordu");
                }
            });
            editPrescription = new Button("Edytuj historię choroby i receptę", buttonClickEvent ->{
                Patient patientPrescription = patientService.findByNameAndLastName(nameField2.getValue(),lastNameField2.getValue());
                Long id = Long.parseLong(prescriptionId.getValue());
                Optional<Prescription> prescriptionExists = prescriptionService.findPrescriptionUsingId(id);
                Prescription prescriptionFound = null;
                if(patientPrescription != null) {
                    if (prescriptionExists.isPresent()) {
                        prescriptionFound = prescriptionExists.get();
                        prescriptionFound.setDisease(disease.getValue());
                        prescriptionFound.setMedicine(medicine.getValue());
                        prescriptionFound.setDose(dose.getValue());
                        prescriptionFound.setRoute(route.getValue());
                        prescriptionFound.setFrequency(frequency.getValue());
                        DateTimeFormatter formatter1 = DateTimeFormatter.ISO_DATE;
                        LocalDate date1 = LocalDate.parse(startDate.getValue(), formatter1);
                        LocalDate date2 = LocalDate.parse(endDate.getValue(), formatter1);
                        prescriptionFound.setStartDate(date1);
                        prescriptionFound.setEndDate(date2);
                        prescriptionFound.setRemarks(remarks.getValue());
                        prescriptionFound.setDoctor(doctor);
                        prescriptionFound.setPatient(patientPrescription);
                        prescriptionService.setPrescription( prescriptionFound, doctor.getDoctorId(), patientPrescription.getPatientId());
                        formFilled = false;
                        removeAll();
                        prescriptionsPanel();
                    }
                }
                if(patientPrescription == null)
                    Notification.show("Nie istnieje taki pacjent");

            });
            if(!prescriptionId.isEmpty() && formFilled){
                createPrescription.setEnabled(false);
                editPrescription.setEnabled(true);
            }else if(prescriptionId.isEmpty()){
                createPrescription.setEnabled(true);
                editPrescription.setEnabled(false);
            }

            vl2.add(searchAllPrescriptions,hl,searchPrescriptionsUsingPatient,prescriptionGrid);
            hl2.add(createPrescription,editPrescription);
            vl3.add(label,prescriptionId,fillEditForm,prescriptionId,fillEditForm,nameField2,lastNameField2,disease,medicine,dose,route,frequency,startDate,endDate,remarks,hl2);

        }
        if(zalogowany && patient !=null){
            prescriptionGrid = new Grid<>(Prescription.class);
            prescriptionGrid.setColumns("prescriptionId", "disease", "medicine", "dose","route", "frequency","startDate","endDate","remarks");
            prescriptionGrid.getColumnByKey("prescriptionId").setHeader("Id:").setWidth("50px");
            prescriptionGrid.getColumnByKey("disease").setHeader("Nazwa choroby:");
            prescriptionGrid.getColumnByKey("medicine").setHeader("Nazwa lekarstwa:");
            prescriptionGrid.getColumnByKey("dose").setHeader("Dawka:");
            prescriptionGrid.getColumnByKey("route").setHeader("Droga podania lekarstwa:");
            prescriptionGrid.getColumnByKey("frequency").setHeader("Częstotliwość:");
            prescriptionGrid.getColumnByKey("startDate").setHeader("Dzień rozpoczęcia przyjmowania leku:");
            prescriptionGrid.getColumnByKey("endDate").setHeader("Dzień końca przyjmowania leku:");
            prescriptionGrid.getColumnByKey("remarks").setHeader("Uwagi, zalecenia:");
            prescriptionGrid.setItems(prescriptionService.findByPatient(patient.getPatientId()));
            prescriptionGrid.setThemeName("wrap-cell-content");
            vl2.add(prescriptionGrid);
        }
        vl.add(h1,h2info,returnToPreviousPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl,vl2,vl3);
    }
    public void appointmentsPanel(){
        vl = new VerticalLayout();
        vl2 = new VerticalLayout();
        hl = new HorizontalLayout();
        hl2 = new HorizontalLayout();
        hl3 = new HorizontalLayout();
        h1 = new H1("Gabinet Lekarski Medic");
        h2info = new H2("Wizyty");

        if(zalogowany && doctor !=null){
            datePicker = new DatePicker();
            datePicker.setLabel("Dzień");
            datePicker.setValue(LocalDate.now());
            datePicker.setLocale(Locale.GERMANY);
            datePicker.addValueChangeListener( e->{
                appointmentGrid.setItems(appointmentService.findByDoctorAndDate(doctor.getDoctorId(),datePicker.getValue()));
            });
            timePicker = new TimePicker();
            timePicker.setLabel("Godzina");
            hl.add(datePicker,timePicker);
            appointmentGrid = new Grid<>(Appointment.class);
            appointmentGrid.setColumns("appointmentDate","appointmentStartTime","appointmentEndTime","roomNumber");
            appointmentGrid.getColumnByKey("appointmentDate").setHeader("Data wizyty: ");
            appointmentGrid.getColumnByKey("appointmentStartTime").setHeader("Godzina rozpoczęcia wizyty: ");
            appointmentGrid.getColumnByKey("appointmentEndTime").setHeader("Godzina zakończenia wizyty: ");
            appointmentGrid.getColumnByKey("roomNumber").setHeader("Numer pokoju: ");
            appointmentGrid.setItems(appointmentService.findByDoctorAndDate(doctor.getDoctorId(),datePicker.getValue()));
            nameField = new TextField("Imię pacjenta");
            lastNameField = new TextField("Nazwisko pacjenta");
            hl2.add(nameField,lastNameField);
            appointmentDetails = new Label();
            searchAppointments = new Button("Wyszukaj wizyty pacjenta", buttonClickEvent -> {
                Patient patient = patientService.findByNameAndLastName(nameField.getValue(),lastNameField.getValue());
                appointmentGrid.setItems(appointmentService.findByPatient(patient.getPatientId()));

            });
            appointmentGrid.addItemClickListener(e -> {
                selectedAppointment = e.getItem();
                appointmentDetails.setText("Wizyta: (Lekarz)) " +selectedAppointment.getDoctor().getName() +
                        " " +selectedAppointment.getDoctor().getLastName()+ " - " + selectedAppointment.getPatient().getName()
                        + " " + selectedAppointment.getPatient().getLastName() + " (Pacjent) godzina: " + selectedAppointment.getAppointmentStartTime().toString());
            });
            appointmentGrid.addItemDoubleClickListener( e ->{
                appointmentToEdit = e.getItem();
                datePicker.setValue(appointmentToEdit.getAppointmentDate());
                timePicker.setValue(appointmentToEdit.getAppointmentStartTime());
                nameField2.setValue(appointmentToEdit.getPatient().getName());
                lastNameField2.setValue(appointmentToEdit.getPatient().getLastName());
                roomNumberField.setValue(appointmentToEdit.getRoomNumber());
                editAppointment.setEnabled(true);
            });

            deleteAppointment = new Button("Usuń wybraną wizytę", buttonClickEvent -> {
                if(selectedAppointment!=null){
                    long id= selectedAppointment.getAppointmentId();
                    appointmentService.deleteAppointmentById(id);
                    appointmentGrid.setItems(appointmentService.findByDoctorAndDate(doctor.getDoctorId(),datePicker.getValue()));
                }else{
                    Notification.show("Nie wybrano wizyty!");
                }
            });
            editAppointment = new Button("Edytuj wizytę (kliknij dwa razy na rekord tabeli by aktywować)", buttonClickEvent -> {
                appointmentToEdit.setAppointmentDate(datePicker.getValue());
                appointmentToEdit.setAppointmentStartTime(timePicker.getValue());
                appointmentToEdit.setAppointmentEndTime(timePicker.getValue().plusMinutes(50));
                appointmentToEdit.setRoomNumber(roomNumberField.getValue());
                Patient patientInEdit = patientService.findByNameAndLastName(nameField2.getValue(),lastNameField2.getValue());
                if(patientInEdit != null) {
                    appointmentService.setAppointment(appointmentToEdit, doctor.getDoctorId(),patientInEdit.getPatientId());
                    appointmentGrid.setItems(appointmentService.findByDoctorAndDate(doctor.getDoctorId(),datePicker.getValue()));
                } else {
                    Notification.show("Nie ma takiego pacjenta");
                }
            });
            editAppointment.setEnabled(false);
            label = new Label("Stwórz wizytę");
            nameField2 = new TextField("Imię pacjenta");
            lastNameField2 = new TextField("Nazwisko pacjenta");

            roomNumberField = new TextField("Numer pokoju");
            createAppointment = new Button("Stwórz wizytę na dzień i godzine wybraną z pól nad tabelą", buttonClickEvent -> {
                Patient patientFound = patientService.findByNameAndLastName(nameField2.getValue(),lastNameField2.getValue());
                if(patientFound == null){
                    Notification.show("Pacjent nie istnieje");
                }else {
                    appointmentService.setAppointment(new Appointment(
                            datePicker.getValue(),
                            timePicker.getValue(),
                            timePicker.getValue().plusMinutes(50),roomNumberField.getValue()),
                            doctor.getDoctorId(),
                            patientFound.getPatientId());
                    appointmentGrid.setItems(appointmentService.findByDoctorAndDate(doctor.getDoctorId(),datePicker.getValue()));
                }
            });
            hl3.add(createAppointment,editAppointment);
            vl2.add(hl,appointmentDetails,appointmentGrid,hl2,searchAppointments,deleteAppointment,label,nameField2,lastNameField2,roomNumberField,hl3);
        }
        if(zalogowany && patient !=null){
            datePicker = new DatePicker();
            datePicker.setLabel("Dzień");
            datePicker.setValue(LocalDate.now());
            datePicker.setLocale(Locale.GERMANY);
            datePicker.addValueChangeListener( e->{
                appointmentGrid.setItems(appointmentService.findByPatientAndDate(patient.getPatientId(),datePicker.getValue()));
            });
            appointmentGrid = new Grid<>(Appointment.class);
            appointmentGrid.setColumns("appointmentDate","appointmentStartTime","appointmentEndTime","roomNumber");
            appointmentGrid.getColumnByKey("appointmentDate").setHeader("Data wizyty: ");
            appointmentGrid.getColumnByKey("appointmentStartTime").setHeader("Godzina rozpoczęcia wizyty: ");
            appointmentGrid.getColumnByKey("appointmentEndTime").setHeader("Godzina zakończenia wizyty: ");
            appointmentGrid.getColumnByKey("roomNumber").setHeader("Numer pokoju: ");
            appointmentGrid.setItems(appointmentService.findByPatientAndDate(patient.getPatientId(),datePicker.getValue()));
            appointmentDetails = new Label();
            appointmentGrid.addItemClickListener(e -> {
                selectedAppointment = e.getItem();
                appointmentDetails.setText("Wizyta: (Lekarz)) " +selectedAppointment.getDoctor().getName() +
                        " " +selectedAppointment.getDoctor().getLastName()+ " - " + selectedAppointment.getPatient().getName()
                        + " " + selectedAppointment.getPatient().getLastName() + " (Pacjent) godzina: " + selectedAppointment.getAppointmentStartTime().toString());
            });
            appointmentInfo = new Label("W przypadku chęci zmiany wizyty lub jej całkowitego odwołania prosimy o skontaktowanie się z recepcją gabinetu lekarskiego lub z samym lekarzem (informacje kontaktowe widoczne na głównej stronie)");
            vl2.add(datePicker,appointmentDetails,appointmentGrid,appointmentInfo);
        }
        returnToPreviousPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,h2info,returnToPreviousPanel,vl2);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        add(vl);
    }

}
