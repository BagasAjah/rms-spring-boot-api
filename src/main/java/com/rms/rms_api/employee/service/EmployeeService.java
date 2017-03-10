package com.rms.rms_api.employee.service;

import java.util.List;

import com.rms.rms_api.employee.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	
	public List<Employee> getAllEmployeesWithOutDetails();
	
	public Employee getAllEmployeesById(String guid);
	
	public String saveOrUpdate(Employee employee);
	
	public void delete(String guid);
}
