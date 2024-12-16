package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.persistence.entity.VisitEntity;

import java.util.Collections;
import java.util.stream.Collectors;




public class PatientMapper {


    // Mapowanie PatientEntity do PatientTO
    public static PatientTO toTransferObject(PatientEntity patientEntity) {
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
                .map(VisitMapper::toTO)
                .collect(Collectors.toList()));

        // Mapowanie adresu
        if (patientEntity.getAddress() != null) {
            patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));
        }

        return patientTO;
    }



    // Mapowanie PatientTO do PatientEntity
    public static PatientEntity toEntity(PatientTO patientTO) {
        PatientEntity patientEntity = new PatientEntity();
        updateEntity(patientEntity, patientTO);
        return patientEntity;
    }

    // Aktualizacja istniejącego PatientEntity na podstawie PatientTO
    public static void updateEntity(PatientEntity patientEntity, PatientTO patientTO) {
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setIsInsured(patientTO.getIsInsured());
        if (patientTO.getAddress() != null) {
            patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));
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
                visitEntity.getDoctor().getFirstName(),
                visitEntity.getDoctor().getLastName(),
                visitEntity.getPatient().getFirstName(),
                visitEntity.getPatient().getLastName(),
                visitEntity.getMedicalTreatment() != null ?
                        Collections.singletonList(visitEntity.getMedicalTreatment().getType().toString()) :
                        Collections.emptyList(),
                visitEntity.getMedicalTreatment() != null ?
                        visitEntity.getMedicalTreatment().getDescription() : null // Dodanie nazwy zabiegu
        );
    }


}
