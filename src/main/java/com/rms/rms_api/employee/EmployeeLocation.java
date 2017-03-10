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

@Entity
public class EmployeeLocation {

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "employeeLocationGuid", length=36)
	private String employeeLocationGuid;
	
	@Column(name = "employeeGuid", insertable = false, updatable = false, nullable = false, length=36)
	private String employeeGuid;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "officeStartDate")
	private Date officeStartDate;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "officeEndDate")
	private Date officeEndDate;
	
	@Column(name = "officeLocation", length=40)
	private String officeLocation;
	
	@Column(name = "officeAddress", length=255)
	private String officeAddress;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeGuid", nullable = false)
	private Employee employee;

	public String getEmployeeLocationGuid() {
		return employeeLocationGuid;
	}

	public void setEmployeeLocationGuid(String employeeLocationGuid) {
		this.employeeLocationGuid = employeeLocationGuid;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public Date getOfficeStartDate() {
		return officeStartDate;
	}

	public void setOfficeStartDate(Date officeStartDate) {
		this.officeStartDate = officeStartDate;
	}

	public Date getOfficeEndDate() {
		return officeEndDate;
	}

	public void setOfficeEndDate(Date officeEndDate) {
		this.officeEndDate = officeEndDate;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
