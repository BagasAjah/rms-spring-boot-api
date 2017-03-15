package com.rms.rms_api.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.QEmployee;
import com.rms.rms_api.employee.EmployeeHistory;
import com.rms.rms_api.employee.repository.EmployeeRepository;
import com.rms.rms_api.employee.repository.JobDescHistoryRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JobDescHistoryRepository jobDescHistoryRepository;

	@Override
	public List<Employee> getAllEmployees(String search, Pageable pageable) {
		List<Employee> employees = new ArrayList<>();
		if (StringUtils.isEmpty(search) && pageable.getSort() == null) {
			employeeRepository.findAllByOrderByFirstNameAsc(pageable).forEach(employees::add);
		} else {
			QEmployee employee = QEmployee.employee;
//			employeeRepository
//					.findAll((employee.firstName.likeIgnoreCase("%" + search + "%")
//							.or(employee.lastName.likeIgnoreCase("%" + search + "%"))), pageable)
//					.forEach(employees::add);
			employeeRepository
			.findAll((employee.firstName.containsIgnoreCase(search)
					.or(employee.lastName.containsIgnoreCase(search))), pageable)
			.forEach(employees::add);
//			Predicate predicate = employee.gender.eq("M");
//			employeeRepository
//					.findAll(predicate, pageable)
//					.forEach(employees::add);
		}
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
	
	@Override
	public List<Employee> getAllEmployeesWithOutDetails(String search, Pageable pageable) {
		List<Employee> employees = new ArrayList<>();
		if (pageable.getSort().equals(null)) {
			employeeRepository.findAllByOrderByFirstNameAsc(pageable).forEach(employees::add);
		} else {
			QEmployee employee = QEmployee.employee;
			employeeRepository
					.findAll((employee.firstName.likeIgnoreCase("%" + search + "%")
							.or(employee.lastName.likeIgnoreCase("%" + search + "%"))), pageable)
					.forEach(employees::add);
		}
		for (Employee employee : employees) {
			employee.setFamilyMember(new ArrayList<>());
			employee.setGradeHistory(new ArrayList<>());
			employee.setLocation(new ArrayList<>());
			employee.setHistory(new ArrayList<>());
		}
		return employees;
	}
	
	@Override
	public Employee getEmployeeById(String guid) {
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

	@Override
	public long getTotalEmployee(String search) {
		QEmployee employee = QEmployee.employee;
		return employeeRepository.count(
				(employee.firstName.containsIgnoreCase(search).or(employee.lastName.containsIgnoreCase(search))));
	}

}
