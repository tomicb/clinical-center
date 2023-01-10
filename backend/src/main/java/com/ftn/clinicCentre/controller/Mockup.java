package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/mockup")
public class Mockup {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ClinicAdministratorService clinicAdministratorService;

    @Autowired
    private ClinicCentreAdministratorService clinicCentreAdministratorService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private MedicalRecordService medicalRecordService;


    @GetMapping()
    public void getDB() {

        Authority authorityCCA = new Authority("ROLE_CLINIC_CENTRE_ADMINISTRATOR");
        Authority authorityCA = new Authority("ROLE_CLINIC_ADMINISTRATOR");
        Authority authorityDoctor = new Authority("ROLE_DOCTOR");
        Authority authorityNurse = new Authority("ROLE_NURSE");
        Authority authorityPatient = new Authority("ROLE_PATIENT");

        List<Authority> authoritiesCCA = new ArrayList<>();
        authoritiesCCA.add(authorityCCA);

        List<Authority> authoritiesCA = new ArrayList<>();
        authoritiesCA.add(authorityCA);

        List<Authority> authoritiesDoctor = new ArrayList<>();
        authoritiesDoctor.add(authorityDoctor);

        List<Authority> authoritiesNurse = new ArrayList<>();
        authoritiesNurse.add(authorityNurse);

        List<Authority> authoritiesPatient = new ArrayList<>();
        authoritiesPatient.add(authorityPatient);

        List<Authority> authoritiesDoctorCCA = new ArrayList<>();
        authoritiesDoctorCCA.add(authorityDoctor);
        authoritiesDoctorCCA.add(authorityCCA);

        PriceListItem pri1 = new PriceListItem("Ocni pregled", 2300.0, "Celokupni pregled ociju");
        PriceListItem pri2 = new PriceListItem("Ultrazvuk sa kolor doplerom", 5500.0, "Ultrazvučni pregled je potpuno bezopasna metoda pregleda unutrašnjih organa. Tehnika pregleda se zasniva na zvučnim talasima visoke frekfencije koji prolaze kroz tkiva i organe, odbijaju se različitom brzinom, vraćaju se ka sondi i vizealizuju na ekranu.");
        PriceListItem pri3 = new PriceListItem("Rengen", 4200.0, "Radiografija je bezbolna i neinvazivna dijagnostička metoda koja se zasniva na primeni X zraka za snimanje različitih delova tela i organa.");
        PriceListItem pri4 = new PriceListItem("Magnetna rezonancija", 2100.0, "Magnetna rezonancija je neinvazivna i precizna dijagnostička metoda, koja daje konkretnu sliku o zdravlju pojedinih organa, organskih sistema, ali i o stanju čitavog organizma.");
        PriceListItem pri5 = new PriceListItem("Endoskopija", 3200.0, "Endoskopijom se otkriva šta je uzrok različitih gastro problema, kao što su mučnine, često povraćanje, osećaj nadutosti i muke, bol u stomaku...");

        List<PriceListItem> cenovnik1 = new ArrayList<>();
        cenovnik1.add(pri1);
        cenovnik1.add(pri2);

        List<PriceListItem> cenovnik2 = new ArrayList<>();
        cenovnik2.add(pri4);
        cenovnik2.add(pri3);

        List<PriceListItem> cenovnik3 = new ArrayList<>();
        cenovnik3.add(pri5);

        Clinic clinic = new Clinic("Klinicki centar Moja mala klinika",  "Mike Lazica 12, Beograd", "Najbolja klinika u gradu", cenovnik1, 0.0);
        Clinic clinic1 = new Clinic("Klinicki centar Paprikovac",  "Marka Miletica 12, Paprikovac", "Paprikovac je najveći privatni zdravstveni sistem u Srbiji, osnovan 2002. godine.", cenovnik2, 3.5);
        Clinic clinic2 = new Clinic("Klinicki centar Dr. Branko Lazic",  "Danila Kisa 132, Ruma", "Nudimo sve usluge lecenja znaci lecimo sve zivo", cenovnik3, 1.5);

        ClinicAdministrator clinicAdministrator1 = new ClinicAdministrator("Miloš", "Živković", "1510996543123", "Jovana Civjića 13, Novi Sad", "milos.zivkovic@gmail.com", passwordEncoder.encode("password1234"), EGender.MALE, authoritiesCA, EUserStatus.APPROVED, clinic);
        ClinicAdministrator clinicAdministrator2 = new ClinicAdministrator("Vladimir", "Majkić", "1712987894284", "Danila Kiša 123b, Beograd", "vladam987@gmail.com", passwordEncoder.encode("malims"), EGender.MALE, authoritiesCA, EUserStatus.APPROVED, clinic1);
        ClinicAdministrator clinicAdministrator3 = new ClinicAdministrator("Borislav", "Mićun", "0803991739853", "Radnička 87, Novi Sad", "micunb@gmail.com", passwordEncoder.encode("delijesever"), EGender.MALE, authoritiesCA, EUserStatus.APPROVED, clinic2);
        ClinicAdministrator clinicAdministrator4 = new ClinicAdministrator("Anastasija", "Šaš", "0104989873384", "Milutina Bojića 33, Subotica", "sas.anastasija@gmail.com", passwordEncoder.encode("visnjica"), EGender.FEMALE, authoritiesCA, EUserStatus.APPROVED, clinic);
        ClinicAdministrator clinicAdministrator5 = new ClinicAdministrator("Vanja", "Matić", "2704992874483", "Milutina Savića 13, Maradik", "vmatic92@gmail.com", passwordEncoder.encode("maticv"), EGender.FEMALE, authoritiesCA, EUserStatus.APPROVED, clinic);
        ClinicAdministrator clinicAdministrator6 = new ClinicAdministrator("Momir", "Tozovac", "0909996234965", "Dr Đorđe Natošević 23, Stara Pazova", "tozovacmomir@gmail.com", passwordEncoder.encode("tozole"), EGender.MALE, authoritiesCA, EUserStatus.APPROVED, clinic2);

        ClinicCentreAdministrator clinicCentreAdministrator = new ClinicCentreAdministrator("Nenad", "Zivkovic", "0303986532785", "Kralja Petra 22, Indjija", "nenad.zivkovic@gmail.com", passwordEncoder.encode("djumbus"), EGender.MALE, authoritiesCCA, EUserStatus.APPROVED);

        Nurse nurse = new Nurse("Marija", "Ilic", "1809997326874", "Milosa Obranovica 33, Novi Sad", "marijai@gmail.com", passwordEncoder.encode("sifrica"), EGender.FEMALE, authoritiesNurse, EUserStatus.APPROVED, clinic);
        Nurse nurse1 = new Nurse("Jovana", "Jovic", "1809997326666", "Bulevar oslobodjenja 68, Novi Sad", "jovana@gmail.com", passwordEncoder.encode("digidi"), EGender.FEMALE, authoritiesNurse, EUserStatus.APPROVED, clinic1);

        Doctor doctor = new Doctor("Zivko", "Miletic", "1307995254451", "Vuka Karadzica 142, Novi Sad", "zivko.miletic@gmail.com", passwordEncoder.encode("smurfg"), EGender.MALE, authoritiesDoctorCCA, 4.6, EUserStatus.APPROVED, clinic);
        Doctor doctor1 = new Doctor("Milorad", "Zivkovic", "0607995258734", "Milutina Milankovića 7, Beograd", "zivkovic@gmail.com", passwordEncoder.encode("kukus"), EGender.MALE, authoritiesDoctor, 3.6, EUserStatus.APPROVED, clinic);
        Doctor doctor2 = new Doctor("Slavko", "Jelovac", "1307995254102", "Bulevar Mihaila Pupina 115,Beograd", "slave@gmail.com", passwordEncoder.encode("zeki"), EGender.MALE, authoritiesDoctor, 2.6, EUserStatus.APPROVED, clinic);
        Doctor doctor3 = new Doctor("Marko", "Popovic", "2511992256418", "Bulevar Zorana Đinđića 121, Nis", "marko.pop@gmail.com", passwordEncoder.encode("zaza"), EGender.MALE, authoritiesDoctor, 4.9, EUserStatus.APPROVED, clinic1);
        Doctor doctor4 = new Doctor("Nemanja", "Savic", "1707995254458", "Bulevar Vojvode Bojovića 6-8, Nis", "nemanja.savic@gmail.com", passwordEncoder.encode("ribice"), EGender.MALE, authoritiesDoctor, 4.9, EUserStatus.APPROVED, clinic1);
        Doctor doctor5 = new Doctor("Anica", "Sladic", "1107995283158", "Milentija Popovića 7b, Beograd", "sladic.a@gmail.com", passwordEncoder.encode("duplapunjena"), EGender.FEMALE, authoritiesDoctor, 4.1, EUserStatus.APPROVED, clinic1);
        Doctor doctor6 = new Doctor("Tamara", "Dimitrijevic", "2207995222118", "Kraljice Marije 3, Novi Sad", "tamara.d@gmail.com", passwordEncoder.encode("pasulj"), EGender.FEMALE, authoritiesDoctor, 4.8, EUserStatus.APPROVED, clinic2);
        Doctor doctor7 = new Doctor("Branimir", "Tomic", "1510000880333", "Milosa Bikovica 13, Novi Banovci", "tomic.brane04@gmail.com", passwordEncoder.encode("lukacileb"), EGender.MALE, authoritiesDoctorCCA, 5.0, EUserStatus.APPROVED, clinic2);


        Patient patient = new Patient("Milica", "Radosavljevic", "1502987698514", "Kristofera Kolumba 13, Beograd", "milicar@gmail.com",passwordEncoder.encode("becarac"), EGender.FEMALE, authoritiesPatient, EUserStatus.APPROVED);
        Patient patient1 = new Patient("Milan", "Zivkovic", "2403982698510", "Kristofera Kolumba 13, Beograd", "zivkovicm@gmail.com", passwordEncoder.encode("tomicjabuke"), EGender.MALE, authoritiesPatient, EUserStatus.APPROVED);
        Patient patient2 = new Patient("Dragan", "Vasic", "1301988698711", "Cara Lazara 13, Beograd", "dragan@gmail.com",passwordEncoder.encode("ducaperejabukeunovomslankamenu"), EGender.MALE, authoritiesPatient, EUserStatus.APPROVED);
        Patient patient3 = new Patient("Aleksa", "Alfirovic", "1611965628112", "Bulevar Cara Dusana 13, Beograd", "aleksaalfirovic00@gmail.com", passwordEncoder.encode("mundir"), EGender.MALE, authoritiesPatient, EUserStatus.APPROVED);

        int pon = 5;
        int uto = 6;
        int sre = 7;
        int cet = 8;
        int pet = 9;
        int sub = 10;

        Examination eexamination = new Examination(doctor, patient, LocalDateTime.of(2021, 1, pon,10,30), LocalDateTime.of(2021, 1,pon,12,0), 2300.0, 0, "Pacijent je obavio preglede za vid. Ustanovljena mu je kratkovidost na desnom oku.", "Ocni pregled");
        Examination eexamination1 = new Examination(doctor1, patient1, LocalDateTime.of(2021, 1,pon,15,30), LocalDateTime.of(2021, 1,pon,17,0), 5500.0, 0, "Obavljen je ultrazvuk sa doplerom. Rezultati pokazuju da je sve u najboljem redu.", "Ultrazvuk sa kolor doplerom");
        Examination eexamination2 = new Examination(doctor2, patient2, LocalDateTime.of(2021, 2,uto,16,30), LocalDateTime.of(2021, 2,uto,17,30), 4200.0, 10, "Pacijent je odradio rengen. Snimak pokazuje napuknuce kolena desne noge.", "Rengen");
        Examination eexamination3 = new Examination(doctor3, patient, LocalDateTime.of(2021, 2,cet,17,30), LocalDateTime.of(2021, 2,cet,18,30), 3200.0, 20, "Obavljena endoskopija, sve je proslo u najboljem redu.", "Endoskopija");
        Examination eexamination4 = new Examination(doctor2, patient1, LocalDateTime.of(2021, 2,sre,15,30), LocalDateTime.of(2021, 2,sre,17,0), 2100.0, 15, "Na pacijentu je uspesno izvrsena magnetna rezonancija.", "Magnetna rezonancija");
        Examination eexamination5 = new Examination(doctor4, patient2, LocalDateTime.of(2021, 3,sre,10,30), LocalDateTime.of(2021, 3,sre,12,0), 5500.0, 20, "Uspesno je obavljen je ultrazvuk sa doplerom. Imali smo malih problema jer je pacijent bio uplasen.", "Ultrazvuk sa kolor doplerom");
        Examination eexamination6 = new Examination(doctor5, patient, LocalDateTime.of(2021, 4,cet,11,30), LocalDateTime.of(2021, 4,cet,12,30), 4200.0, 10, "Odradjen rengen na pacijentu. Na snimku nema vidljivih preloma, leva noga izgleda dobro.", "Rengen");
        Examination eexamination7 = new Examination(doctor1, patient1, LocalDateTime.of(2021, 4,pet,9,30), LocalDateTime.of(2021, 4,pet,10,45), 2300.0, 0, "Odradjen ocni pregled, pacijent je kratkovid.", "Ocni pregled");
        Examination eexamination8 = new Examination(doctor2, patient, LocalDateTime.of(2021, 5,sub,12,30), LocalDateTime.of(2021, 5,sub,14,0), 2100.0, 0, "Pacijent je bio uplasen, ali je ipak sve dobro proslo.", "Magnetna rezonancija");
        Examination examination = new Examination(doctor, patient, LocalDateTime.of(2021, 6, pon,10,30), LocalDateTime.of(2021, 6,pon,12,0), 2300.0, 0, "Pacijent je obavio preglede za vid. Ustanovljena mu je kratkovidost na desnom oku.", "Ocni pregled");
        Examination examination1 = new Examination(doctor1, patient1, LocalDateTime.of(2021, 7,pon,15,30), LocalDateTime.of(2021, 7,pon,17,0), 5500.0, 0, "Obavljen je ultrazvuk sa doplerom. Rezultati pokazuju da je sve u najboljem redu.", "Ultrazvuk sa kolor doplerom");
        Examination examination2 = new Examination(doctor2, patient2, LocalDateTime.of(2021, 7,uto,16,30), LocalDateTime.of(2021, 7,uto,17,30), 4200.0, 10, "Pacijent je odradio rengen. Snimak pokazuje napuknuce kolena desne noge.", "Rengen");
        Examination examination3 = new Examination(doctor3, patient, LocalDateTime.of(2021, 7,cet,17,30), LocalDateTime.of(2021, 7,cet,18,30), 3200.0, 20, "Obavljena endoskopija, sve je proslo u najboljem redu.", "Endoskopija");
        Examination examination4 = new Examination(doctor2, patient1, LocalDateTime.of(2021, 7,sre,15,30), LocalDateTime.of(2021, 7,sre,17,0), 2100.0, 15, "Na pacijentu je uspesno izvrsena magnetna rezonancija.", "Magnetna rezonancija");
        Examination examination5 = new Examination(doctor4, patient2, LocalDateTime.of(2021, 7,sre,10,30), LocalDateTime.of(2021, 7,sre,12,0), 5500.0, 20, "Uspesno je obavljen je ultrazvuk sa doplerom. Imali smo malih problema jer je pacijent bio uplasen.", "Ultrazvuk sa kolor doplerom");
        Examination examination6 = new Examination(doctor5, patient, LocalDateTime.of(2021, 7,cet,11,30), LocalDateTime.of(2021, 7,cet,12,30), 4200.0, 10, "Odradjen rengen na pacijentu. Na snimku nema vidljivih preloma, leva noga izgleda dobro.", "Rengen");
        Examination examination7 = new Examination(doctor1, patient1, LocalDateTime.of(2021, 7,pet,9,30), LocalDateTime.of(2021, 7,pet,10,45), 2300.0, 0, "Odradjen ocni pregled, pacijent je kratkovid.", "Ocni pregled");
        Examination examination8 = new Examination(doctor2, patient, LocalDateTime.of(2021, 7,sub,12,30), LocalDateTime.of(2021, 7,sub,14,0), 2100.0, 0, "Pacijent je bio uplasen, ali je ipak sve dobro proslo.", "Magnetna rezonancija");


        Examination examination9 = new Examination(doctor, null, LocalDateTime.of(2021, 7, pon + 7,10,30), LocalDateTime.of(2021, 7,pon + 7,12,0), 0.0, 0, "", "Available examination");
        Examination examination10 = new Examination(doctor1, null, LocalDateTime.of(2021, 7,pon + 7,15,30), LocalDateTime.of(2021, 7,pon + 7,17,0), 0.0, 0, "", "Available examination");
        Examination examination11 = new Examination(doctor2, null, LocalDateTime.of(2021, 7,uto + 7,16,30), LocalDateTime.of(2021, 7,uto + 7,17,30), 0.0, 0, "", "Available examination");
        Examination examination12 = new Examination(doctor3, null, LocalDateTime.of(2021, 7, cet + 7,17,30), LocalDateTime.of(2021, 7,cet + 7,18,30), 0.0, 0, "", "Available examination");
        Examination examination13 = new Examination(doctor2, null, LocalDateTime.of(2021, 7, sre + 7,15,30), LocalDateTime.of(2021, 7,sre + 7,17,0), 0.0, 0, "", "Available examination");

        MedicalRecord medicalRecord = new MedicalRecord();
        MedicalRecordItem mdi = new MedicalRecordItem("Dioptrija", "1.2");
        List<MedicalRecordItem> listMDI = new ArrayList<>();
        listMDI.add(mdi);
        medicalRecord.setItems(listMDI);
        medicalRecord.setPatient(patient);
        medicalRecord.getExaminations().add(eexamination);
        medicalRecord.getExaminations().add(eexamination3);
        medicalRecord.getExaminations().add(eexamination6);
        medicalRecord.getExaminations().add(eexamination8);
        medicalRecord.getExaminations().add(examination);
        medicalRecord.getExaminations().add(examination3);
        medicalRecord.getExaminations().add(examination6);
        medicalRecord.getExaminations().add(examination8);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        MedicalRecordItem mdi2 = new MedicalRecordItem("Alergija na lek", "Bromazepan");
        MedicalRecordItem mdi5 = new MedicalRecordItem("Visina", "173cm");
        List<MedicalRecordItem> listMDI2 = new ArrayList<>();
        listMDI2.add(mdi2);
        listMDI2.add(mdi5);
        medicalRecord2.setItems(listMDI2);
        medicalRecord2.setPatient(patient1);
        medicalRecord2.getExaminations().add(eexamination1);
        medicalRecord2.getExaminations().add(eexamination4);
        medicalRecord2.getExaminations().add(eexamination7);
        medicalRecord2.getExaminations().add(examination1);
        medicalRecord2.getExaminations().add(examination4);
        medicalRecord2.getExaminations().add(examination7);

        MedicalRecord medicalRecord3 = new MedicalRecord();
        MedicalRecordItem mdi3 = new MedicalRecordItem("Tezina", "178kg");
        MedicalRecordItem mdi4 = new MedicalRecordItem("Visina", "188cm");
        List<MedicalRecordItem> listMDI3 = new ArrayList<>();
        listMDI3.add(mdi3);
        listMDI3.add(mdi4);
        medicalRecord3.setItems(listMDI3);
        medicalRecord3.setPatient(patient2);
        medicalRecord3.getExaminations().add(eexamination2);
        medicalRecord3.getExaminations().add(eexamination5);
        medicalRecord3.getExaminations().add(examination2);
        medicalRecord3.getExaminations().add(examination5);

        MedicalRecord medicalRecord4 = new MedicalRecord();
        medicalRecord4.setPatient(patient3);

        Recipe recipe = new Recipe(ERecipeStatus.PENDING, LocalDateTime.now(), null);
        List<Medication> medications = new ArrayList<>();
        medications.add(new Medication("BROMAZEPAM 20mg"));
        medications.add(new Medication("PROPAFENONE 70mg"));
        medications.add(new Medication("ASPIRIN 170mg"));
        recipe.setMedication(medications);
        recipe.setExamination(examination);
        examination.setRecipe(recipe);

        authorityService.save(authorityCA);
        authorityService.save(authorityCCA);
        authorityService.save(authorityDoctor);
        authorityService.save(authorityNurse);
        authorityService.save(authorityPatient);

        clinicService.save(clinic);
        clinicService.save(clinic1);
        clinicService.save(clinic2);

        clinicAdministratorService.save(clinicAdministrator1);
        clinicAdministratorService.save(clinicAdministrator2);
        clinicAdministratorService.save(clinicAdministrator3);
        clinicAdministratorService.save(clinicAdministrator4);
        clinicAdministratorService.save(clinicAdministrator5);
        clinicAdministratorService.save(clinicAdministrator6);

        clinicCentreAdministratorService.save(clinicCentreAdministrator);

        doctorService.save(doctor);
        doctorService.save(doctor1);
        doctorService.save(doctor2);
        doctorService.save(doctor3);
        doctorService.save(doctor4);
        doctorService.save(doctor5);
        doctorService.save(doctor6);
        doctorService.save(doctor7);

        patientService.save(patient);
        patientService.save(patient1);
        patientService.save(patient2);
        patientService.save(patient3);

        examinationService.save(eexamination);
        examinationService.save(eexamination1);
        examinationService.save(eexamination2);
        examinationService.save(eexamination3);
        examinationService.save(eexamination4);
        examinationService.save(eexamination5);
        examinationService.save(eexamination6);
        examinationService.save(eexamination7);
        examinationService.save(eexamination8);
        examinationService.save(examination);
        examinationService.save(examination1);
        examinationService.save(examination2);
        examinationService.save(examination3);
        examinationService.save(examination4);
        examinationService.save(examination5);
        examinationService.save(examination6);
        examinationService.save(examination7);
        examinationService.save(examination8);

        examinationService.save(examination9);
        examinationService.save(examination10);
        examinationService.save(examination11);
        examinationService.save(examination12);
        examinationService.save(examination13);

        medicalRecordService.save(medicalRecord);
        medicalRecordService.save(medicalRecord2);
        medicalRecordService.save(medicalRecord3);
        medicalRecordService.save(medicalRecord4);

        nurseService.save(nurse);
        nurseService.save(nurse1);
    }
}