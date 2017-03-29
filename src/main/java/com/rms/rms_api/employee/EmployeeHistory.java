package com.rms.rms_api.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rms.rms_api.common.CustomJsonDateSerializer;
import com.rms.rms_api.common.entity.RecordStatusEntity;

@Entity
@FilterDef(name = "activeFilter")
public class EmployeeHistory extends RecordStatusEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "employeeHistoryGuid", length=36)
	private String employeeHistoryGuid;
	
	@Column(name = "employeeGuid", insertable = false, updatable = false, nullable = false, length=36)
	private String employeeGuid;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "historyStartDate")
	private Date historyStartDate;

    @JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "historyEndtDate")
	private Date historyEndtDate;
	
	@Column(name = "company", length=20)
	private String company;
	
	@Column(name = "position", length=20)
	private String position;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeGuid", nullable = false)
	private Employee employee;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeHistory", cascade = CascadeType.ALL)
	@Filter(name="activeFilter", condition="1 = record_statusid")
	private List<JobDescList> jobDescList = new ArrayList<>();	

	public String getEmployeeHistoryGuid() {
		return employeeHistoryGuid;
	}

	public void setEmployeeHistoryGuid(String employeeHistoryGuid) {
		this.employeeHistoryGuid = employeeHistoryGuid;
	}

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public Date getHistoryStartDate() {
		return historyStartDate;
	}

	public void setHistoryStartDate(Date historyStartDate) {
		this.historyStartDate = historyStartDate;
	}

	public Date getHistoryEndtDate() {
		return historyEndtDate;
	}

	public void setHistoryEndtDate(Date historyEndtDate) {
		this.historyEndtDate = historyEndtDate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<JobDescList> getJobDescList() {
		return jobDescList;
	}

	public void setJobDescList(List<JobDescList> jobDescList) {
		this.jobDescList = jobDescList;
	}

	public void addNewJobdesc(String name, EmployeeHistory history) {
		JobDescList jobDesc = new JobDescList();
		jobDesc.setJebDescName(name);
		jobDesc.setEmployeeHistory(history);
		jobDescList.add(jobDesc);
	}
}
