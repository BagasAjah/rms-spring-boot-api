package com.rms.rms_api.employee.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rms.rms_api.common.RMSConstant;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeGrade;

@Service
public class EmployeeGradeServiceImpl implements EmployeeGradeService {

	@Override
	public Employee mergeEmployeeGrade(Employee existing, Employee updated) {
		addOrUpdatedGradeDetail(existing, updated);
		deletedGradeDetail(existing, updated);
		
		return existing;
	}
	
	private void addOrUpdatedGradeDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeGrade updatedGrade : updatedEmployee.getGradeHistory()) {
			boolean found = false;
			for(EmployeeGrade existingGrade : existingEmployee.getGradeHistory()) {
				if (StringUtils.equalsIgnoreCase(existingGrade.getEmployeeGradeGuid(), updatedGrade.getEmployeeGradeGuid())) {
					found = true;
					existingGrade.setEmployee(updatedEmployee);
					existingGrade.setDs(updatedGrade.getDs());
					existingGrade.setEndDate(updatedGrade.getEndDate());
					existingGrade.setGrade(updatedGrade.getGrade());
					existingGrade.setStartDate(updatedGrade.getStartDate());
				}
			}
			if (!found) {
				updatedGrade.setEmployee(updatedEmployee);
				updatedGrade.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				existingEmployee.getGradeHistory().add(updatedGrade);
			}
		}
	}
	
	private void deletedGradeDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeGrade existingGrade : existingEmployee.getGradeHistory()) {
			boolean found = false;
			for(EmployeeGrade updatedGrade : updatedEmployee.getGradeHistory()) {
				if (StringUtils.equalsIgnoreCase(existingGrade.getEmployeeGradeGuid(), updatedGrade.getEmployeeGradeGuid())) {
					found = true;
				}
			}
			if (!found) {
				existingGrade.setRecordStatusID(RMSConstant.RECORD_STATUS_DELETED);
			}
		}
	}

}
