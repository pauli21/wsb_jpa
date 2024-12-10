package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.PatientDaoCustom;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class PatientDaoImpl implements PatientDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void addVisit(Long patientId, Long doctorId, String visitDescription, java.time.LocalDateTime visitDate, Long treatmentId) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);
        MedicalTreatmentEntity medicalTreatment = entityManager.find(MedicalTreatmentEntity.class, treatmentId);

        if (medicalTreatment == null) {
            throw new IllegalArgumentException("Invalid treatment ID provided");
        }

        VisitEntity newVisit = new VisitEntity();
        newVisit.setPatient(patient);
        newVisit.setDoctor(doctor);
        newVisit.setDescription(visitDescription);
        newVisit.setTime(visitDate);
        newVisit.setMedicalTreatment(medicalTreatment);


//        newVisit.setDoctor(entityManager.find(DoctorEntity.class, doctorId));
        patient.getVisits().add(newVisit);
        entityManager.merge(patient);
    }
}
