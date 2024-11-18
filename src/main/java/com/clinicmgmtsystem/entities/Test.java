package com.clinicmgmtsystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

public class Test {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long testId;
	private String testName;
	private Double testPrice;
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Double getTestPrice() {
		return testPrice;
	}
	public void setTestPrice(Double testPrice) {
		this.testPrice = testPrice;
	}
	

}
