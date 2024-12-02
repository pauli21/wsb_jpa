package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testAddVisit() {
        // Arrange
        Long patientId = 1L; // Zakładając, że istnieje pacjent z ID 1 w data.sql
        Long doctorId = 1L;  // Zakładając, że istnieje doktor z ID 1 w data.sql
        String description = "Kontrola";
        LocalDateTime visitDate = LocalDateTime.now();

        // Act
        patientDao.addVisit(patientId, doctorId, description, visitDate);

        // Assert
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        assertNotNull(patient, "Patient should not be null");
        assertFalse(patient.getVisits().isEmpty(), "Patient should have visits");

        VisitEntity visit = patient.getVisits().get(0);
        assertEquals(description, visit.getDescription(), "Description should match");
        assertEquals(visitDate, visit.getTime(), "Visit date should match");
        assertEquals(doctorId, visit.getDoctor().getId(), "Doctor ID should match");
    }

    @Test
    @Transactional
    public void testDeletePatient() {
        Long patientId = 1L;
        patientService.deletePatient(patientId);

        assertThrows(RuntimeException.class, () -> patientService.getPatientById(patientId));
    }


    @Test
    public void testGetPatientById() {
        Long patientId = 1L;
        PatientTO patientTO = patientService.getPatientById(patientId);

        assertNotNull(patientTO);
        assertEquals(patientId, patientTO.getId());
        assertNotNull(patientTO.getVisits());
        assertTrue(patientTO.getVisits().size() > 0);  // Sprawdzamy, że pacjent ma wizyty
        assertNotNull(patientTO.getAdditionalField());  // Sprawdzamy dodatkowe pole
    }

}
