package com.jpacourse.persistence.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Version;

@Entity
@Table(name = "PATIENT")
public class PatientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version; // Pole wersji do obsługi optymistycznego blokowania

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String telephoneNumber;

	private String email;

	@Column(nullable = false)
	private String patientNumber;

	@Column(nullable = false)
	private LocalDate dateOfBirth;

	/**
	 * Nowe pole z ubezpieczeniem
	 */
	private Boolean isInsured;

	/**
	 * Nowa kolumna - wiek pacjenta
	 */
	@Column(nullable = false)
	private Integer age;



	/**
	 * Relacja jednostronna z {@link AddressEntity}, w której klasa PatientEntity
	 * jest stroną zależną (dziecko). Każdy pacjent ma przypisany dokładnie jeden adres.
	 *
	 * <p>Kolumna {@code address_id} w tabeli reprezentuje klucz obcy wskazujący na encję AddressEntity.
	 * Adres jest wymagany, dlatego atrybut {@code nullable} jest ustawiony na {@code false}.
	 */
	@ManyToOne
	@JoinColumn(name = "address_id")
	private AddressEntity address;

	/**
	 * Lista wizyt powiązanych z danym pacjentem.
	 *
	 * <p>Relacja dwukierunkowa z {@link VisitEntity}, gdzie klasa PatientEntity
	 * jest stroną nadrzędną (rodzic), a klasa VisitEntity jest właścicielem relacji.
	 *
	 * <p>Atrybut {@code mappedBy = "patient"} wskazuje, że właścicielem relacji jest pole
	 * {@code patient} w klasie VisitEntity. Dzięki {@code CascadeType.ALL} wszystkie operacje
	 * na pacjencie są kaskadowo wykonywane na powiązanych wizytach.
	 * Opcja {@code orphanRemoval = true} zapewnia usunięcie wizyt, które zostaną
	 * usunięte z listy.
	 */
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisitEntity> visits ;
//			= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPatientNumber() {
		return patientNumber;
	}

	public void setPatientNumber(String patientNumber) {
		this.patientNumber = patientNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Boolean getIsInsured() {
		return isInsured;
	}

	public void setIsInsured(Boolean isInsured) {
		this.isInsured = isInsured;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	public List<VisitEntity> getVisits() {
		return visits;
	}

	public void setVisits(List<VisitEntity> visits) {
		this.visits = visits;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
