package com.clinicmgmtsystem.entities;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity

public class Doctor extends User {

    private String specialty;
    private LocalTime startTime;
    private LocalTime endTime;

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

   
}