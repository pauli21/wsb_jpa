package com.jpacourse.persistence.dao.impl;

import com.jpacourse.persistence.dao.DoctorDao;
import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.DoctorEntity;
import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Autowired
    private DoctorDao doctorDao;


    @Transactional
    @Override
    public void addVisit(Long patientId, Long doctorId, String visitDescription, java.time.LocalDateTime visitDate, Long treatmentId) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = doctorDao.findOne(doctorId);
        MedicalTreatmentEntity medicalTreatment = entityManager.find(MedicalTreatmentEntity.class, treatmentId); // TODO to tez z innego DAO powinno isc

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
