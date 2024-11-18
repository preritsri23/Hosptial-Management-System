package com.clinicmgmtsystem.entities;

import jakarta.persistence.Entity;

@Entity
public class Patient  extends User{
	private Integer age;
	private String medHistory;
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getMedHistory() {
		return medHistory;
	}
	public void setMedHistory(String medHistory) {
		this.medHistory = medHistory;
	}


}
