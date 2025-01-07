package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.AddressEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private AddressDao addressDao;  // Tworzymy DAO dla AddressEntity

    @PersistenceContext
    private EntityManager entityManager;


    @Before
    @Transactional
    public void setUp() {
// Przygotowanie danych testowych

        // Tworzymy adres testowy
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("Wroclawska");
        address.setAddressLine2("Mieszkanie 10");
        address.setCity("Warszawa");
        address.setPostalCode("00-005");

        // Zapisz adres w pamięci (do H2 bazy)
        address = addressDao.save(address);  // Zakładając, że masz dostęp do repozytorium

        // Tworzymy pacjenta testowego
        PatientEntity patient = new PatientEntity();
//        patient.setId(1L);
        patient.setFirstName("Katarzyna");
        patient.setLastName("Mazur");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("katarzyna.mazur@przyklad.com");
        patient.setPatientNumber("P001");
        patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient.setAddress(address);
        patient.setAge(38);
//        AddressEntity address = addressDao.findById(1L).orElseThrow(() -> new RuntimeException("Adres nie został znaleziony!"));
//        patient.setAddress(address);

        // Dodajemy pacjenta do bazy danych
        patientDao.save(patient);
    }


    @Test
    @Transactional
    public void testAddVisit() {
        // Arrange
        Long patientId = 1L; // Zakładając, że istnieje pacjent z ID 1 w data.sql
        Long doctorId = 1L;  // Zakładając, że istnieje doktor z ID 1 w data.sql
        Long treatmentId = 1L; // Zakładając, że istnieje zabieg o ID 1 w data.sql
        String description = "Kontrola";
//        LocalDateTime visitDate = LocalDateTime.now();
        LocalDateTime visitDate = LocalDateTime.of(2024, 12, 01, 10, 0, 0, 0); // Ustalona data


        // Act
        patientDao.addVisit(patientId, doctorId, description, visitDate, treatmentId);  // Dodano treatmentId

        // Assert
        PatientEntity patient = patientDao.findOne(patientId);
        assertNotNull(patient, "Patient should not be null");
        assertFalse(patient.getVisits().isEmpty(), "Patient should have visits");

        VisitEntity visit = patient.getVisits().get(0);
        assertEquals(description, visit.getDescription(), "Description should match");
        assertEquals(visitDate, visit.getTime(), "Visit date should match");
        assertEquals(doctorId, visit.getDoctor().getId(), "Doctor ID should match");
    }

    @Test
    @Transactional
    public void testDeletePatient()
    {
        Long patientId = 1L;
//        patientDao.delete(patientId);
        patientDao.delete(patientId);

        assertThat(patientDao.findOne(patientId)).isNull();
    }




    @Test
    @Transactional
    public void testFindByLastName() {
        // Arrange
        String lastName = "Mazur";  // Zakładając, że pacjent o tym nazwisku został dodany w @Before

        // Act
        List<PatientEntity> patients = patientDao.findByLastName(lastName);

        // Debug - Wydrukuj dane pacjentów w bazie danych
        System.out.println("Patients in database:");
        patients.forEach(patient ->
                System.out.println("ID: " + patient.getId() + ", First Name: " + patient.getFirstName() + ", Last Name: " + patient.getLastName())
        );

        // Assert
        assertNotNull(patients, "Patients list should not be null");
        assertFalse(patients.isEmpty(), "Patients list should not be empty");

        PatientEntity patient = patients.get(0);
        assertEquals(lastName, patient.getLastName(), "Last name should match");
    }


    @Test
    @Transactional
    public void testFindByVisitsGreaterThan() {
        // Wartość visitCount, dla której chcemy znaleźć pacjentów
        int visitCount = 1;

        // Wywołanie metody DAO
        List<PatientEntity> patients = patientDao.findByVisitsGreaterThan(visitCount);

        // Weryfikacja wyników
        assertThat(patients).isNotEmpty(); // Upewnij się, że wynik nie jest pusty
        patients.forEach(patient ->
                assertThat(patient.getVisits().size()).isGreaterThan(visitCount)
        ); // Każdy pacjent powinien mieć więcej niż visitCount wizyt
    }


    @Test
    public void testFindByAgeGreaterThan() {
        // Arrange
        Integer ageThreshold = 30;

        // Act
        List<PatientEntity> patients = patientDao.findByAgeGreaterThan(ageThreshold);

        // Assert
        assertThat(patients).isNotEmpty();
        patients.forEach(patient ->
                assertThat(patient.getAge()).isGreaterThan(ageThreshold)
        );
    }

    @Test
    @Transactional
    public void testOptimisticLocking() {
        System.out.println("=== TEST START ===");

        // Pobierz i odłącz pierwszą instancję
        PatientEntity patient1 = patientDao.findOne(1L);
        System.out.println("Patient1 loaded: " + patient1);
        System.out.println("Patient1 ID: " + patient1.getId());
        System.out.println("Patient1 Version: " + patient1.getVersion());

        entityManager.detach(patient1); // Upewnij się, że encja jest odłączona
        System.out.println("Patient1 detached.");

        // Pobierz drugą instancję
        PatientEntity patient2 = patientDao.findOne(1L);
        System.out.println("Patient2 loaded: " + patient2);
        System.out.println("Patient2 ID: " + patient2.getId());
        System.out.println("Patient2 Version: " + patient2.getVersion());

        // Zmień i zapisz pierwszą instancję
        patient1.setFirstName("JohnUpdated");
        System.out.println("Patient1 FirstName updated to: " + patient1.getFirstName());

        try {
            patientDao.save(patient1); // Aktualizacja powoduje inkrementację wersji
            System.out.println("Patient1 saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving Patient1: " + e.getMessage());
            e.printStackTrace();
        }

        // Weryfikuj wersję w bazie danych po zapisie
        System.out.println("Patient1 version after save: " + patient1.getVersion());

        // Zmień i spróbuj zapisać drugą instancję
        patient2.setLastName("DoeUpdated");
        System.out.println("Patient2 LastName updated to: " + patient2.getLastName());

        System.out.println("Patient2 version before save: " + patient2.getVersion());

        try {
            patientDao.save(patient2); // Powinno rzucić wyjątek
            System.out.println("Patient2 saved successfully (unexpected).");
        } catch (OptimisticLockException e) {
            System.out.println("Expected OptimisticLockException caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected exception caught while saving Patient2: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== TEST END ===");
    }


}
