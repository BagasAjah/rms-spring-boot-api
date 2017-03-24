package com.rms.rms_api.employee.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.rms.rms_api.common.SearchCriteria;
import com.rms.rms_api.employee.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees(List<SearchCriteria> criterias, Pageable pageable) throws Exception;
	
	public List<Employee> getAllEmployeesWithOutDetails(List<SearchCriteria> criterias, Pageable pageable) throws Exception;
	
	public Employee getEmployeeById(String guid);
	
	public Employee findByID(String guid);
	
	public String save(Employee employee);
	
	public String update(Employee employee) throws Exception;
	
	public void delete(String guid);
	
	public long getTotalEmployee(List<SearchCriteria> listCriteria) throws Exception;
}
