package com.clinicmgmtsystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity

public class Pathalogist extends User {
    private Integer labNum;

	public Integer getLabNum() {
		return labNum;
	}

	public void setLabNum(Integer labNum) {
		this.labNum = labNum;
	}
    
}
