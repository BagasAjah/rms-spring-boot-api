package com.rms.rms_api.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeHistory;
import com.rms.rms_api.employee.repository.EmployeeRepository;
import com.rms.rms_api.employee.repository.JobDescHistoryRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JobDescHistoryRepository jobDescHistoryRepository;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAllByOrderByFirstNameAsc().forEach(employees::add);
		for (Employee employee : employees) {
			if (!employee.getHistory().isEmpty()) {
				for (EmployeeHistory history : employee.getHistory()) {
					List<String> jobdescList = new ArrayList<>();
					jobDescHistoryRepository.findByEmployeeHistoryGuid(history.getEmployeeHistoryGuid())
							.forEach(jobdesc -> jobdescList.add(jobdesc.getJebDescName()));
					history.setJobDesc(jobdescList);
				}
			}
		}
		return employees;
	}
	
	public List<Employee> getAllEmployeesWithOutDetails() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAll().forEach(employees::add);
		for (Employee employee : employees) {
			employee.setFamilyMember(new ArrayList<>());
			employee.setGradeHistory(new ArrayList<>());
			employee.setLocation(new ArrayList<>());
			employee.setHistory(new ArrayList<>());
		}
		return employees;
	}
	
	public Employee getAllEmployeesById(String guid) {
		Employee employee = new Employee();
		employee = employeeRepository.findOne(guid);
		if (employee != null && !employee.getHistory().isEmpty()) {
			for (EmployeeHistory history : employee.getHistory()) {
				List<String> jobdescList = new ArrayList<>();
				jobDescHistoryRepository.findByEmployeeHistoryGuid(history.getEmployeeHistoryGuid())
						.forEach(jobdesc -> jobdescList.add(jobdesc.getJebDescName()));
				history.setJobDesc(jobdescList);
			}
		}
		return employee;
	}

	@Override
	public String saveOrUpdate(Employee employee) {
		employee.getGradeHistory().forEach(grade -> grade.setEmployee(employee));
		employee.getFamilyMember().forEach(family -> family.setEmployee(employee));
		employee.getLocation().forEach(location -> location.setEmployee(employee));
		employee.getHistory().forEach(history -> history.setEmployee(employee));
		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee.getEmployeeGuid();
	}

	@Override
	public void delete(String guid) {
		employeeRepository.delete(guid);
	}

}
