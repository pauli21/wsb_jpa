package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.*;
import com.jpacourse.persistence.enums.TreatmentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.jpacourse.persistence.enums.Specialization;

import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional  /// dodałam możliwe ze do zmiany?
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao; /// dodałam możliwe ze do zmiany?


    @PersistenceContext
    private EntityManager entityManager;



    @BeforeEach
    void setUp() {
        System.out.println("[SETUP] Tworzenie danych testowych dla pacjenta...");

        // Tworzenie danych adresowych
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Test Street");
        address.setAddressLine2("Apt 4B");
        address.setCity("Test City");
        address.setPostalCode("12-345");

        address = addressDao.save(address);  // Zapisz adres

        // Tworzenie danych testowych dla pacjenta
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("5551234567");
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("P123");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setIsInsured(true);
        patient.setAddress(address);
        patient.setAge(38);

        patient = patientDao.save(patient);  // Zapisz pacjenta w bazie

        // Tworzenie danych dla wizyty
        VisitEntity visit = new VisitEntity();
        visit.setDescription("Kontrola");
        visit.setTime(java.time.LocalDateTime.of(2025, 1, 1, 10, 0));
        visit.setPatient(patient);

        // Tworzenie lekarza (musisz upewnić się, że jest lekarz o ID 1 w bazie)
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, 1L); // Lekarz o ID 1
        if (doctor == null) {
            // Tworzymy lekarza jeśli nie istnieje
            doctor = new DoctorEntity();
            doctor.setFirstName("Jan");
            doctor.setLastName("Kowalski");
            doctor.setTelephoneNumber("123456789");
            doctor.setEmail("jan.kowalski@przyklad.com");
            doctor.setDoctorNumber("D001");
            doctor.setSpecialization(Specialization.SURGEON);
            doctor.setAddress(address);
            doctor = entityManager.merge(doctor);
        }
        visit.setDoctor(doctor);

        // Tworzenie leczenia (upewnij się, że jest leczenie o ID 1)
        MedicalTreatmentEntity treatment = entityManager.find(MedicalTreatmentEntity.class, 1L); // Leczenie o ID 1
        if (treatment == null) {
            // Tworzymy leczenie, jeśli nie istnieje
            treatment = new MedicalTreatmentEntity();
            treatment.setDescription("USG brzucha");
            treatment.setType(TreatmentType.USG);
            treatment = entityManager.merge(treatment);
        }
        visit.setMedicalTreatment(treatment);

        // Dodanie wizyty do pacjenta
        if (patient.getVisits() == null) {
            patient.setVisits(new ArrayList<>());  // Inicjalizujemy listę wizyt
        }
        patient.getVisits().add(visit);

        patientDao.save(patient);  // Zapisz pacjenta z wizytą

        // Sprawdzenie danych
        PatientEntity retrievedPatient = patientDao.findOne(patient.getId());
        System.out.println("Wizyty pacjenta po zapisie: " + retrievedPatient.getVisits());

        System.out.println("[SETUP] Pacjent zapisany w bazie danych z ID: " + patient.getId());
        System.out.println("[SETUP] Adres pacjenta zapisany: " + patient.getAddress().getId());
    }



    @Test
    @Transactional
    public void testDeletePatient() {
        Long patientId = 1L;
        System.out.println("[TEST] testDeletePatient - Usuwanie pacjenta o ID: " + patientId);

        // Wywołanie metody usuwającej pacjenta
        patientService.deletePatient(patientId);
        System.out.println("[TEST] Wywołano metodę deletePatient.");

        // Sprawdzenie, że pacjent został usunięty
        PatientEntity patient = patientDao.findOne(patientId);
        System.out.println("[TEST] Wartość patient po usunięciu: " + patient);
        assertThat(patient).describedAs("Pacjent powinien zostać usunięty").isNull();
    }

    @Test
    public void testGetPatientById() {
//        Long patientId = 1L;
        PatientEntity patient = patientDao.findByLastName("Doe").get(0);
        Long patientId = patient.getId();
        System.out.println("[TEST] testGetPatientById - Pobieranie pacjenta o ID: " + patientId);

        // Pobranie danych pacjenta
        PatientTO patientTO = patientService.getPatientById(patientId);
        System.out.println("[TEST] Otrzymane dane pacjenta: " + patientTO);

        // Weryfikacja poprawności zwróconych danych
        assertThat(patientTO).isNotNull();
        System.out.println("[TEST] Pacjent jest niepusty");
        assertThat(patientTO.getId()).isEqualTo(patientId);
        System.out.println("[TEST] ID pacjenta jest poprawne");
        System.out.println("[TEST] Wizyty pacjenta: " + patientTO.getVisits());
        assertThat(patientTO.getVisits()).isNotNull();
        assertThat(patientTO.getVisits()).isNotEmpty();
        System.out.println("[TEST] Wizyty pacjenta są poprawnie puste");
        assertThat(patientTO.getIsInsured()).isNotNull();
        assertThat(patientTO.getIsInsured()).isTrue();
        System.out.println("[TEST] Status ubezpieczenia pacjenta jest poprawny");
    }

    @Test
    public void testFindVisitsByPatientId() {
        Long patientId = 1L;
        System.out.println("[TEST] testFindVisitsByPatientId - Pobieranie wizyt pacjenta o ID: " + patientId);

        // Pobranie danych pacjenta
        PatientTO patient = patientService.getPatientById(patientId);
        assertThat(patient).isNotNull().describedAs("Pacjent powinien istnieć w bazie danych");

        // Pobranie listy wizyt
        List<VisitTO> visits = patient.getVisits();
        assertThat(visits).isNotNull().describedAs("Lista wizyt nie powinna być pusta");
        assertThat(visits).isNotEmpty().describedAs("Pacjent powinien mieć co najmniej jedną wizytę");

        // Weryfikacja danych wizyt
        visits.forEach(visit -> {
            System.out.println("[TEST] Sprawdzanie wizyty: ID " + visit.getId() + ", Opis: " + visit.getDescription());
            assertThat(visit.getVisitTime()).isNotNull().describedAs("Data wizyty nie powinna być pusta");
        });
    }


}
