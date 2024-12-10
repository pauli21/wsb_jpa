package com.jpacourse.service;


import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PatientServiceTest {

//    @Autowired
    @MockBean
    private PatientService patientService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void testDeletePatient() {
        Long patientId = 1L;

        patientService.deletePatient(patientId);

//        assertThrows(RuntimeException.class, () -> patientService.getPatientById(patientId));

        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        assertNull(patient, "Patient should be deleted");

        // Sprawdzenie wizyt
        assertTrue(patient.getVisits().isEmpty(), "Patient's visits should be deleted");

//        // Sprawdzenie, że lekarze pozostali
        assertNotNull(entityManager.find(VisitEntity.class, 1L));
    }

    @Test
    public void testGetPatientById() {
        Long patientId = 1L;

        PatientTO patientTO = patientService.getPatientById(patientId);

        assertThrows(RuntimeException.class, () -> patientService.getPatientById(patientId), "Patient should not be found after deletion");
        assertNotNull(patientTO);
        assertEquals(patientId, patientTO.getId());
        assertNotNull(patientTO.getVisits());
        assertFalse(patientTO.getVisits().isEmpty());
        assertNotNull(patientTO.getIsInsured());
        assertTrue(patientTO.getIsInsured());
    }
}
