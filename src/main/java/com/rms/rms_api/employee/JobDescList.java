package com.rms.rms_api.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rms.rms_api.common.entity.RecordStatusEntity;

@Entity
@FilterDef(name = "activeFilter")
@Filter(name="activeFilter", condition="1 = record_statusid")
public class JobDescList extends RecordStatusEntity{
	
	@Id
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "jebDescGuid", length=36)
	private String jebDescGuid;
	
	@Column(name = "employeeHistoryGuid", insertable = false, updatable = false, nullable = false, length=36)
	private String employeeHistoryGuid;
	
	@Column(name = "jebDescName")
	private String jebDescName;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeHistoryGuid", nullable = false)
	private EmployeeHistory employeeHistory;

	public String getJebDescGuid() {
		return jebDescGuid;
	}

	public void setJebDescGuid(String jebDescGuid) {
		this.jebDescGuid = jebDescGuid;
	}

	public String getEmployeeHistoryGuid() {
		return employeeHistoryGuid;
	}

	public void setEmployeeHistoryGuid(String employeeHistoryGuid) {
		this.employeeHistoryGuid = employeeHistoryGuid;
	}

	public String getJebDescName() {
		return jebDescName;
	}

	public void setJebDescName(String jebDescName) {
		this.jebDescName = jebDescName;
	}

	public EmployeeHistory getEmployeeHistory() {
		return employeeHistory;
	}

	public void setEmployeeHistory(EmployeeHistory employeeHistory) {
		this.employeeHistory = employeeHistory;
	}
	
	
}
