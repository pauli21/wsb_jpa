package com.jpacourse.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String city;

	private String addressLine1;

	private String addressLine2;

	private String postalCode;

	/**
	 * Relacja dwukierunkowa z {@link DoctorEntity}, w której klasa AddressEntity
	 * jest stroną nadrzędną (rodzic). Adres może być przypisany do wielu lekarzy.
	 *
	 * <p>Pole {@code doctors} reprezentuje listę lekarzy powiązanych z danym adresem.
	 * Atrybut {@code mappedBy = "address"} wskazuje, że właścicielem relacji jest klasa DoctorEntity.
	 * Dzięki {@code CascadeType.ALL} operacje na encji AddressEntity propagują się na powiązanych lekarzy.
	 */
	@OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
	private List<DoctorEntity> doctors = new ArrayList<>();

	/**
	 * Relacja dwukierunkowa z {@link PatientEntity}, w której klasa AddressEntity
	 * jest stroną nadrzędną (rodzic). Adres może być przypisany do wielu pacjentów.
	 *
	 * <p>Pole {@code patients} reprezentuje listę pacjentów powiązanych z danym adresem.
	 * Atrybut {@code mappedBy = "address"} wskazuje, że właścicielem relacji jest klasa PatientEntity.
	 * Dzięki {@code CascadeType.ALL} operacje na encji AddressEntity propagują się na powiązanych pacjentów.
	 */
	@OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
	private List<PatientEntity> patients = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
