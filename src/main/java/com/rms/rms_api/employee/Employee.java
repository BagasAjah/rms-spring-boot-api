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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rms.rms_api.common.CustomJsonDateSerializer;
import com.rms.rms_api.common.XmlDateTimeAdapter;

@Entity
@FilterDef(name = "activeFilter")
public class Employee {

	@Id
	@Column(name = "employeeGuid", length = 36)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String employeeGuid;

	@NotNull
	@Column(name = "firstName", length = 40)
	private String firstName;

	@Column(name = "lastName", length = 40)
	private String lastName;

	@NotNull
	@Column(name = "gender", length = 1)
	private String gender;

	@NotNull
	@XmlJavaTypeAdapter(value = XmlDateTimeAdapter.class, type = Date.class)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "dob")
	private Date dob;

	@Column(name = "nationality", length = 20)
	private String nationality;

	@Column(name = "maritalStatus", length = 1)
	private String maritalStatus;

	@Column(name = "phone", length = 15)
	private String phone;

	@Column(name = "subDivision", length = 40)
	private String subDivision;

	@Column(name = "status", length = 1)
	private String status;

	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "suspendDate")
	private Date suspendDate;

	@NotNull
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "hireDate")
	private Date hireDate;

	@Column(name = "grade", length = 4)
	private String grade;

	@Column(name = "division", length = 10)
	private String division;

	@Column(name = "email", length = 40)
	private String email;

	@Column(name = "office", length = 20)
	private String office;

	@Lob
	@Column(name = "avatar")
	private byte[] avatar;

	@Transient
	private String base64Image;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	@Filter(name = "activeFilter", condition = "1 = record_statusid")
	private List<EmployeeHistory> history = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	@Filter(name = "activeFilter", condition = "1 = record_statusid")
	private List<EmployeeGrade> gradeHistory = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	@Filter(name = "activeFilter", condition = "1 = record_statusid")
	private List<EmployeeFamily> familyMember = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
	@Filter(name = "activeFilter", condition = "1 = record_statusid")
	private List<EmployeeLocation> location = new ArrayList<>();

	public String getEmployeeGuid() {
		return employeeGuid;
	}

	public void setEmployeeGuid(String employeeGuid) {
		this.employeeGuid = employeeGuid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSuspendDate() {
		return suspendDate;
	}

	public void setSuspendDate(Date suspendDate) {
		this.suspendDate = suspendDate;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public List<EmployeeHistory> getHistory() {
		return history;
	}

	public void setHistory(List<EmployeeHistory> history) {
		this.history = history;
	}

	public List<EmployeeGrade> getGradeHistory() {
		return gradeHistory;
	}

	public void setGradeHistory(List<EmployeeGrade> gradeHistory) {
		this.gradeHistory = gradeHistory;
	}

	public List<EmployeeFamily> getFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(List<EmployeeFamily> familyMember) {
		this.familyMember = familyMember;
	}

	public List<EmployeeLocation> getLocation() {
		return location;
	}

	public void setLocation(List<EmployeeLocation> location) {
		this.location = location;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

}
