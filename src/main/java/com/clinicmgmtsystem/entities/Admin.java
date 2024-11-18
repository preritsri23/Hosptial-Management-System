package com.clinicmgmtsystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity

public class Admin extends User{
    private String adminCode;

	public String getAdminCode() {
		return adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
    
}
