package com.rms.rms_api.employee.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.rms.rms_api.common.RMSConstant;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeHistory;
import com.rms.rms_api.employee.JobDescList;

@Service
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {

	@Override
	public Employee mergeEmployeeHistory(Employee existing, Employee updated) {
		addOrUpdatedHistoryDetail(existing, updated);
		deletedHistoryDetail(existing, updated);
		
		return existing;
	}
	
	private void addOrUpdatedHistoryDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeHistory updatedHistory : updatedEmployee.getHistory()) {
			boolean found = false;
			for(EmployeeHistory existingHistory : existingEmployee.getHistory()) {
				if (StringUtils.equalsIgnoreCase(existingHistory.getEmployeeHistoryGuid(), updatedHistory.getEmployeeHistoryGuid())) {
					found = true;
					existingHistory.setEmployee(updatedEmployee);
					existingHistory.setCompany(updatedHistory.getCompany());
					existingHistory.setHistoryEndtDate(updatedHistory.getHistoryEndtDate());
					existingHistory.setHistoryStartDate(updatedHistory.getHistoryStartDate());
					existingHistory.setPosition(updatedHistory.getPosition());

					addOrUpdatedJobDescDetail(existingHistory.getJobDescList(), updatedHistory);
					deletedJobDescDetail(existingHistory.getJobDescList(), updatedHistory.getJobDescList());
					existingHistory.setJobDescList(existingHistory.getJobDescList());
				}
			}
			if (!found) {
				updatedHistory.setEmployee(updatedEmployee);
				updatedHistory.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				updatedHistory.getJobDescList().forEach(jobdesc -> {
					jobdesc.setEmployeeHistory(updatedHistory);
					jobdesc.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				});
				existingEmployee.getHistory().add(updatedHistory);
			}
		}
	}
	
	private void deletedHistoryDetail(Employee existingEmployee, Employee updatedEmployee) {
		for(EmployeeHistory existingHistory : existingEmployee.getHistory()) {
			boolean found = false;
			for(EmployeeHistory updatedHistory : updatedEmployee.getHistory()) {
				if (StringUtils.equalsIgnoreCase(existingHistory.getEmployeeHistoryGuid(), updatedHistory.getEmployeeHistoryGuid())) {
					found = true;
				}
			}
			if (!found) {
				existingHistory.setRecordStatusID(RMSConstant.RECORD_STATUS_DELETED);
			}
		}
	}
	
	private void addOrUpdatedJobDescDetail(List<JobDescList> existingJobDescs, EmployeeHistory updatedHistory) {
		for(JobDescList updatedJobDesc : updatedHistory.getJobDescList()) {
			boolean found = false;
			for(JobDescList existingJobDesc : existingJobDescs) {
				if (StringUtils.equalsIgnoreCase(existingJobDesc.getJebDescGuid(), updatedJobDesc.getJebDescGuid())) {
					found = true;
					existingJobDesc.setJebDescName(updatedJobDesc.getJebDescName());
				}
			}
			if (!found) {
				updatedJobDesc.setEmployeeHistory(updatedHistory);
				updatedJobDesc.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
				existingJobDescs.add(updatedJobDesc);
			}
			
		}
	}
	
	private void deletedJobDescDetail(List<JobDescList> existingJobDescs, List<JobDescList> updatedJobDescs) {
		for(JobDescList existingJobDesc : existingJobDescs) {
			boolean found = false;
			for(JobDescList updatedJobDesc : updatedJobDescs) {
				if (StringUtils.equalsIgnoreCase(existingJobDesc.getJebDescGuid(), updatedJobDesc.getJebDescGuid())) {
					found = true;
				}
			}
			if (!found) {
				existingJobDesc.setRecordStatusID(RMSConstant.RECORD_STATUS_DELETED);
			}
		}
	}

}
