package com.clinicmgmtsystem.entities;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

public class Medicine {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long medId;
	private String medName;
	private Double medPrice;
	private LocalDate expDate;
	public Long getMedId() {
		return medId;
	}
	public void setMedId(Long medId) {
		this.medId = medId;
	}
	public String getMedName() {
		return medName;
	}
	public void setMedName(String medName) {
		this.medName = medName;
	}
	public Double getMedPrice() {
		return medPrice;
	}
	public void setMedPrice(Double medPrice) {
		this.medPrice = medPrice;
	}
	public LocalDate getExpDate() {
		return expDate;
	}
	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}
	
	
	

}
