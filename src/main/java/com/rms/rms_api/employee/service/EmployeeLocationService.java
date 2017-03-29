package com.rms.rms_api.employee.service;

import com.rms.rms_api.employee.Employee;

public interface EmployeeLocationService {

	public Employee mergeEmployeeLocation(Employee existing, Employee updated);
	
}
