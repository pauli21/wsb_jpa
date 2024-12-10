package com.jpacourse.persistence.dao;

import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.DoctorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface PatientDao extends JpaRepository<PatientEntity, Long>, PatientDaoCustom {

    @Query("SELECT d FROM DoctorEntity d WHERE d.id = :doctorId")
    DoctorEntity findDoctorById(@Param("doctorId") Long doctorId);


}
