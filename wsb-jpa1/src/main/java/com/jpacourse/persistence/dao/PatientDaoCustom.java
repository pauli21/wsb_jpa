package com.jpacourse.persistence.dao;
import com.jpacourse.persistence.entity.PatientEntity;


public interface PatientDaoCustom {
    void addVisit(Long patientId, Long doctorId, String visitDescription, java.time.LocalDateTime visitDate);
}
