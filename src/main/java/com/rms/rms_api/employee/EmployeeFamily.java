package com.rms.rms_api.employee;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rms.rms_api.common.CustomJsonDateSerializer;
import com.rms.rms_api.common.entity.RecordStatusEntity;

@Entity
public class EmployeeFamily extends RecordStatusEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "employeeFamilyGuid", length=36)
	private String employeeFamilyGuid;
	
	@Column(name = "employeeGuid", insertable = false, updatable = false, nullable = false, length=36)
	private String employeeGuid;
	
	@Column(name = "familyName", length=40)
	private String familyName;
	
	@Column(name = "familyGender", length=1)
	private String familyGender;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "familyDob")
	private Date familyDob;
	
	@Column(name = "familyType", length=1)
	private String familyType;
	
	@Column(name = "isActive")
	private Boolean isActive;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeGuid", nullable = false)
	private Employee employee;

	public String getEmployeeFamilyGuid() {
		return employeeFamilyGuid;
	}

	public void setEmployeeFamilyGuid(String employeeFamilyGuid) {
		this.employeeFamilyGuid = employeeFamilyGuid;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFamilyGender() {
		return familyGender;
	}

	public void setFamilyGender(String familyGender) {
		this.familyGender = familyGender;
	}

	public Date getFamilyDob() {
		return familyDob;
	}

	public void setFamilyDob(Date familyDob) {
		this.familyDob = familyDob;
	}

	public String getFamilyType() {
		return familyType;
	}

	public void setFamilyType(String familyType) {
		this.familyType = familyType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
