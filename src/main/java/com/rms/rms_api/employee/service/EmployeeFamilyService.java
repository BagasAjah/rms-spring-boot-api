package com.rms.rms_api.employee.service;

import com.rms.rms_api.employee.Employee;

public interface EmployeeFamilyService {
	public Employee mergeEmployeeFamily(Employee existing, Employee updated);
}
