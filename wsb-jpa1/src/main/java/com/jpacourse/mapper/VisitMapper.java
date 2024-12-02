package com.jpacourse.mapper;

import com.jpacourse.persistence.entity.MedicalTreatmentEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.dto.VisitTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;


@Component
public class VisitMapper {

    public VisitTO toTO(VisitEntity visitEntity) {
        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setVisitTime(visitEntity.getTime());
        visitTO.setDescription(visitEntity.getDescription());
        visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
        visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());



        // Pobieranie opisu zabiegu z pojedynczego obiektu MedicalTreatmentEntity
        visitTO.setMedicalTreatments(
                visitEntity.getMedicalTreatment() != null ?
                        Collections.singletonList(visitEntity.getMedicalTreatment().getDescription()) :
                        Collections.emptyList()
        );

        return visitTO;
    }
}
