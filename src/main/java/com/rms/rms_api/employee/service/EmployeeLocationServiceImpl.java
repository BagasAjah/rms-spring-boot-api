package com.rms.rms_api.employee.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rms.rms_api.common.RMSConstant;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeLocation;

@Service
public class EmployeeLocationServiceImpl implements EmployeeLocationService {

	@Override
	public Employee mergeEmployeeLocation(Employee existing, Employee updated) {

		addOrUpdatedLocationDetail(existing, updated);
		deletedLocationDetail(existing, updated);
		
		return existing;
	}
	
	private void addOrUpdatedLocationDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeLocation updatedLocation : updatedEmployee.getLocation()) {
			boolean found = false;
			for(EmployeeLocation existingLocation : existingEmployee.getLocation()) {
				if (StringUtils.equalsIgnoreCase(existingLocation.getEmployeeLocationGuid(), updatedLocation.getEmployeeLocationGuid())) {
					found = true;
					existingLocation.setEmployee(updatedEmployee);
					existingLocation.setOfficeAddress(updatedLocation.getOfficeAddress());
					existingLocation.setOfficeEndDate(updatedLocation.getOfficeEndDate());
					existingLocation.setOfficeLocation(updatedLocation.getOfficeLocation());
					existingLocation.setOfficeStartDate(updatedLocation.getOfficeStartDate());
				}
			}
			if (!found) {
				updatedLocation.setEmployee(updatedEmployee);
				updatedLocation.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				existingEmployee.getLocation().add(updatedLocation);
			}
		}
	}
	
	private void deletedLocationDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeLocation existingLocation : existingEmployee.getLocation()) {
			boolean found = false;
			for(EmployeeLocation updatedLocation : updatedEmployee.getLocation()) {
				if (StringUtils.equalsIgnoreCase(existingLocation.getEmployeeLocationGuid(), updatedLocation.getEmployeeLocationGuid())) {
					found = true;
				}
			}
			if (!found) {
				existingLocation.setRecordStatusID(RMSConstant.RECORD_STATUS_DELETED);
			}
		}
	}

}
