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
public class EmployeeGrade {

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "employeeGradeGuid", length=36)
	private String employeeGradeGuid;

	@Column(name = "employeeGuid", insertable = false, updatable = false, nullable = false, length=36)
	private String employeeGuid;

	@Column(name = "ds", length = 2)
	private String ds;

	@Column(name = "employeeGrade", length = 4)
	private String grade;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "startDate")
	private Date startDate;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "endDate")
	private Date endDate;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeGuid", nullable = false)
	private Employee employee;
	
	public String getEmployeeGradeGuid() {
		return employeeGradeGuid;
	}

	public void setEmployeeGradeGuid(String employeeGradeGuid) {
		this.employeeGradeGuid = employeeGradeGuid;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
