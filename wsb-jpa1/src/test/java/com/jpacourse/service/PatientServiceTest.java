package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.dao.AddressDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.AddressEntity; /// dodałam możliwe ze do zmiany?
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

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

    @BeforeEach
    void setUp() {
        System.out.println("[SETUP] Tworzenie danych testowych dla pacjenta...");

        // Tworzenie danych adresowych
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("123 Test Street");
        address.setAddressLine2("Apt 4B");
        address.setCity("Test City");
        address.setPostalCode("12-345");

        address = addressDao.save(address); /// dodałam możliwe ze do zmiany?

        // Tworzenie danych testowych dla pacjenta
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setTelephoneNumber("5551234567");
        patient.setEmail("john.doe@example.com");
        patient.setPatientNumber("P123");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setIsInsured(true);
//        patient.setVisits(Collections.emptyList()); // Brak wizyt
        patient.setAddress(address);


        patient.setVisits(new ArrayList<>());

        System.out.println("Wizyty przed zapisem: " + patient.getVisits());


        patientDao.save(patient);
        PatientEntity retrievedPatient = patientDao.findOne(patient.getId());
        System.out.println("Wizyty pacjenta po zapisie: " + retrievedPatient.getVisits());


        System.out.println("[SETUP] Pacjent zapisany w bazie danych z ID: " + patient.getId());
        System.out.println("[SETUP] Adres pacjenta zapisany: " + patient.getAddress().getId());
    }


    @Test
    @Transactional
    public void testDeletePatient() {
        Long patientId = 6L;
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
        Long patientId = 6L;
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
        assertThat(patientTO.getVisits()).isEmpty();
        System.out.println("[TEST] Wizyty pacjenta są poprawnie puste");
        assertThat(patientTO.getIsInsured()).isNotNull();
        assertThat(patientTO.getIsInsured()).isTrue();
        System.out.println("[TEST] Status ubezpieczenia pacjenta jest poprawny");
    }
}
