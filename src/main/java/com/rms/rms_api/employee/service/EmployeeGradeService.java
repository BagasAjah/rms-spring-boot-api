package com.rms.rms_api.employee.service;

import com.rms.rms_api.employee.Employee;

public interface EmployeeGradeService {
	public Employee mergeEmployeeGrade(Employee existing, Employee updated);
}
