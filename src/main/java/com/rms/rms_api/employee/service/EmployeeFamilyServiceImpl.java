package com.rms.rms_api.employee.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rms.rms_api.common.RMSConstant;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeFamily;

@Service
public class EmployeeFamilyServiceImpl implements EmployeeFamilyService {

	@Override
	public Employee mergeEmployeeFamily(Employee existing, Employee updated) {
		addOrUpdatedFamliyDetail(existing, updated);
		deletedFamilyDetail(existing, updated);
		
		return existing;
	}
	
	private void addOrUpdatedFamliyDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeFamily updatedFamily : updatedEmployee.getFamilyMember()) {
			boolean found = false;
			for(EmployeeFamily existingFamily : existingEmployee.getFamilyMember()) {
				if (StringUtils.equalsIgnoreCase(existingFamily.getEmployeeFamilyGuid(), updatedFamily.getEmployeeFamilyGuid())) {
					found = true;
					existingFamily.setEmployee(updatedEmployee);
					existingFamily.setFamilyDob(updatedFamily.getFamilyDob());
					existingFamily.setFamilyGender(updatedFamily.getFamilyGender());
					existingFamily.setFamilyName(updatedFamily.getFamilyName());
					existingFamily.setFamilyType(updatedFamily.getFamilyType());
					existingFamily.setIsActive(updatedFamily.getIsActive());
				}
			}
			if (!found) {
				updatedFamily.setEmployee(updatedEmployee);
				updatedFamily.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				existingEmployee.getFamilyMember().add(updatedFamily);
			}
		}
	}
	
	private void deletedFamilyDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeFamily existingFamily : existingEmployee.getFamilyMember()) {
			boolean found = false;
			for(EmployeeFamily updatedFamily : updatedEmployee.getFamilyMember()) {
				if (StringUtils.equalsIgnoreCase(existingFamily.getEmployeeFamilyGuid(), updatedFamily.getEmployeeFamilyGuid())) {
					found = true;
				}
			}
			if (!found) {
				existingFamily.setRecordStatusID(RMSConstant.RECORD_STATUS_DELETED);
			}
		}
	}

}
