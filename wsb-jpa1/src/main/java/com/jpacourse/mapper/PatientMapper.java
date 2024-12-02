package com.jpacourse.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;



@Component
public class PatientMapper {

    @Autowired
    private VisitMapper visitMapper;

    @Autowired
    private AddressMapper addressMapper;

    // Mapowanie PatientEntity do PatientTO
    public PatientTO toTransferObject(PatientEntity patientEntity) {
        PatientTO patientTO = new PatientTO();

        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setIsInsured(patientEntity.getIsInsured());



        patientTO.setVisits(patientEntity.getVisits().stream()
                .map(this::toVisitTO)
                .collect(Collectors.toList()));

        // Mapowanie adresu
        if (patientEntity.getAddress() != null) {
            patientTO.setAddress(addressMapper.mapToTO(patientEntity.getAddress()));
        }

        return patientTO;
    }



    // Mapowanie PatientTO do PatientEntity
    public PatientEntity toEntity(PatientTO patientTO) {
        PatientEntity patientEntity = new PatientEntity();
        updateEntity(patientEntity, patientTO);
        return patientEntity;
    }

    // Aktualizacja istniejącego PatientEntity na podstawie PatientTO
    public void updateEntity(PatientEntity patientEntity, PatientTO patientTO) {
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setIsInsured(patientTO.getIsInsured());
        if (patientTO.getAddress() != null) {
            patientEntity.setAddress(addressMapper.mapToEntity(patientTO.getAddress()));
        }
    }

    // Mapowanie wizyty na VisitTO
    private VisitTO toVisitTO(VisitEntity visitEntity) {
        // Jeśli wizyta ma przypisany zabieg, tworzymy listę typów zabiegów
        String treatmentTypes = visitEntity.getMedicalTreatment() != null ?
                visitEntity.getMedicalTreatment().getType().toString() : null; // Pobieramy typ zabiegu


        // Modyfikacja, aby pasowało do nowego konstrukora VisitTO
        return new VisitTO(
                visitEntity.getId(),
                visitEntity.getTime(),
                visitEntity.getDoctor().getFirstName(),  // Używamy firstName
                visitEntity.getDoctor().getLastName(),   // Używamy lastName
                visitEntity.getPatient().getFirstName(), // Dodanie pacjenta
                visitEntity.getPatient().getLastName(),
                treatmentTypes != null ? Collections.singletonList(treatmentTypes) : Collections.emptyList()// Przekazujemy listę typów zabiegów
        );
    }


}
