package com.jpacourse.persistence.dao;
import com.jpacourse.persistence.entity.PatientEntity;

import java.time.LocalDateTime;


public interface PatientDaoCustom {
    void addVisit(Long patientId, Long doctorId, String visitDescription, LocalDateTime visitDate, Long treatmentId);
}

