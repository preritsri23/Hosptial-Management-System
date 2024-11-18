package com.clinicmgmtsystem.entities;

import jakarta.persistence.Entity;

@Entity
public class Pharmacist extends User {
 private Integer medStoreNum;

public Integer getMedStoreNum() {
	return medStoreNum;
}

public void setMedStoreNum(Integer medStoreNum) {
	this.medStoreNum = medStoreNum;
}
 
}
