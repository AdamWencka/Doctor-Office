package com.example.DoctorOffice.views;


import com.example.DoctorOffice.model.Doctor;
import com.example.DoctorOffice.model.Patient;
import com.example.DoctorOffice.service.AppointmentService;
import com.example.DoctorOffice.service.DoctorService;
import com.example.DoctorOffice.service.PatientService;
import com.example.DoctorOffice.service.PrescriptionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
    Label label;
    Button loginPatient;
    Button loginDoctor;
    Button goToServiceAndPrices;
    Button returnToPreviusPanel;
    H1 h1;
    H2 h2info;
    H2 h2doctors;
    Paragraph paragraph;
    Image image;
    TextField patientEmail;
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
        loginPatient = new Button("Zaloguj jako pacjent", buttonClickEvent -> {
            removeAll();
            patientLoginPanel();
        });
        loginDoctor = new Button("Zaloguj jako lekarz", buttonClickEvent -> {
            removeAll();
            doctorLoginPanel();
        });
        goToServiceAndPrices = new Button("Usługi i cennik", buttonClickEvent -> {
           removeAll();
           servicesAndPricesPanel();
        });
        hl.add(loginPatient, loginDoctor,goToServiceAndPrices);
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
        add(vl);
    }

    public void patientLoginPanel(){
        vl = new VerticalLayout();
         h1 = new H1("Witaj Pacjencie");
         patientEmail = new TextField("Email");
         password = new PasswordField("Hasło");
        loginButton = new Button("Zaloguj", buttonClickEvent -> {
            if (patientService.loginPatient(patientEmail.getValue(),password.getValue())){
                zalogowany = true;
                Optional patientOptional = patientService.findByEmail(patientEmail.getValue());
                patient = (Patient) patientOptional.get();
                removeAll();
                mainPagePanel();
            }
        });
        returnToPreviusPanel = new Button( "Powrót", buttonClickEvent -> {
           removeAll();
           mainPagePanel();
        });
        vl.add(h1, patientEmail,password,loginButton,returnToPreviusPanel);
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
        returnToPreviusPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,doctorEmail,password,loginButton,returnToPreviusPanel);
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

        returnToPreviusPanel = new Button( "Powrót", buttonClickEvent -> {
            removeAll();
            mainPagePanel();
        });
        vl.add(h1,returnToPreviusPanel,h2info,paragraph);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h1);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,returnToPreviusPanel);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,h2info);
        vl.setHorizontalComponentAlignment(Alignment.CENTER,paragraph);
        add(vl);
    }
}
