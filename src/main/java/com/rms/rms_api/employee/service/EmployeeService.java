package com.rms.rms_api.employee.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.rms.rms_api.employee.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees(String search, Pageable pageable);
	
	public List<Employee> getAllEmployeesWithOutDetails(String search, Pageable pageable);
	
	public Employee getEmployeeById(String guid);
	
	public String saveOrUpdate(Employee employee);
	
	public void delete(String guid);
	
	public long getTotalEmployee(String search);
}
