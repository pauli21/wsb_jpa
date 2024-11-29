package com.jpacourse.persistence.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "VISIT")
public class VisitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@Column(nullable = false)
	private LocalDateTime time;

	/**
	 * Relacja dwukierunkowa z {@link DoctorEntity}, w której klasa VisitEntity
	 * jest właścicielem relacji. Każda wizyta musi być powiązana z jednym lekarzem.
	 *
	 * <p>Kolumna {@code doctor_id} w tabeli reprezentuje klucz obcy wskazujący na encję DoctorEntity.
	 * Adnotacja {@code nullable = false} wymusza obecność lekarza dla każdej wizyty.
	 */
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private DoctorEntity doctor;

	/**
	 * Relacja dwukierunkowa z {@link PatientEntity}, w której klasa VisitEntity
	 * jest właścicielem relacji. Każda wizyta musi być powiązana z jednym pacjentem.
	 *
	 * <p>Kolumna {@code patient_id} w tabeli reprezentuje klucz obcy wskazujący na encję PatientEntity.
	 * Adnotacja {@code nullable = false} wymusza obecność pacjenta dla każdej wizyty.
	 */
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private PatientEntity patient;

	/**
	 * Relacja jednostronna z {@link MedicalTreatmentEntity}, w której klasa VisitEntity
	 * jest stroną zależną (dziecko). Każda wizyta musi być powiązana z jednym zabiegiem medycznym.
	 *
	 * <p>Kolumna {@code treatment_id} w tabeli reprezentuje klucz obcy wskazujący na encję MedicalTreatmentEntity.
	 * Adnotacja {@code nullable = false} wymusza obecność zabiegu dla każdej wizyty.
	 */
	@ManyToOne
	@JoinColumn(name = "treatment_id", nullable = false)
	private MedicalTreatmentEntity medicalTreatment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
