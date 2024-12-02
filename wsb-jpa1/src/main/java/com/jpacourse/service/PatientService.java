package com.jpacourse.service;

import com.jpacourse.persistence.dao.PatientDao;
import com.jpacourse.persistence.entity.PatientEntity;
import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpacourse.persistence.entity.VisitEntity;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

//    @Autowired
    private final PatientDao patientDao;

//    @Autowired
    private final PatientMapper patientMapper;

    @Autowired
    private AddressMapper addressMapper;

    public PatientService(PatientDao patientDao, PatientMapper patientMapper) {
        this.patientDao = patientDao;
        this.patientMapper = patientMapper;
    }



    // Pobieranie pacjenta po ID
    public PatientTO getPatientById(Long id) {
        Optional<PatientEntity> patientEntityOptional = patientDao.findById(id);
        if (!patientEntityOptional.isPresent()) {
            throw new RuntimeException("Patient not found");
        }

        // Mapowanie do TO i zwrócenie
        return patientMapper.toTransferObject(patientEntityOptional.get());
    }





    public PatientTO createPatient(PatientTO patientTO) {
        PatientEntity patientEntity = patientMapper.toEntity(patientTO);
        if (patientTO.getAddress() != null) {
            patientEntity.setAddress(addressMapper.mapToEntity(patientTO.getAddress()));
        }
        patientEntity = patientDao.save(patientEntity);
        return patientMapper.toTransferObject(patientEntity);
    }




    // Usuwanie pacjenta (kaskadowo usunięcie wizyt)
    @Transactional
    public void deletePatient(Long id) {
        PatientEntity patient = patientDao.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
        patientDao.delete(patient);
    }




    public PatientTO updatePatient(Long id, PatientTO patientTO) {
        PatientEntity patientEntity = patientDao.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
        patientMapper.updateEntity(patientEntity, patientTO);
        patientEntity = patientDao.save(patientEntity);
        return patientMapper.toTransferObject(patientEntity);
    }



    // Pobieranie wszystkich pacjentów
    public List<PatientTO> getAllPatients() {
        return patientDao.findAll().stream()
                .map(patientMapper::toTransferObject)
                .collect(Collectors.toList());
    }



    // Dodawanie wizyty do pacjenta (kaskadowo)
    @Transactional
    public void addVisitToPatient(Long patientId, Long doctorId, String description, String visitTime) {
        PatientEntity patient = patientDao.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        LocalDateTime visitDateTime = LocalDateTime.parse(visitTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Tworzymy wizytę i przypisujemy jej odpowiednie dane
        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitDateTime);
        visit.setDoctor(patientDao.findDoctorById(doctorId));  // Zakładając, że masz metodę findDoctorById
        visit.setPatient(patient);
        // Dodajemy wizytę do pacjenta
        patient.getVisits().add(visit);

        // Kaskadowe zapisanie pacjenta (update)
        patientDao.save(patient);
    }


}
