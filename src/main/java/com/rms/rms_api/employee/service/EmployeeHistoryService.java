package com.rms.rms_api.employee.service;

import com.rms.rms_api.employee.Employee;

public interface EmployeeHistoryService {
	public Employee mergeEmployeeHistory(Employee existing, Employee updated);
}
