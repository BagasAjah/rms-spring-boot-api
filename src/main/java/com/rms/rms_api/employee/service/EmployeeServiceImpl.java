package com.rms.rms_api.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.rms.rms_api.common.SearchCriteria;
import com.rms.rms_api.common.SearchFilter;
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

	@Override
	public List<Employee> getAllEmployees(List<SearchCriteria> criterias, Pageable pageable) throws Exception {
		List<Employee> employees = new ArrayList<>();
		if (criterias.isEmpty() && pageable.getSort() == null) {
			employeeRepository.findAllByOrderByFirstNameAsc(pageable).forEach(employees::add);
		} else {
			BooleanExpression predicate = generatePredicate(criterias);
			employeeRepository.findAll(predicate, pageable).forEach(employees::add);
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
	public List<Employee> getAllEmployeesWithOutDetails(List<SearchCriteria> criterias, Pageable pageable) throws Exception {
		List<Employee> employees = new ArrayList<>();
		if (pageable.getSort().equals(null)) {
			employeeRepository.findAllByOrderByFirstNameAsc(pageable).forEach(employees::add);
		} else {
			BooleanExpression predicate = generatePredicate(criterias);
			employeeRepository.findAll(predicate, pageable).forEach(employees::add);
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
	public long getTotalEmployee(List<SearchCriteria> listCriteria) throws Exception {
		BooleanExpression predicate = generatePredicate(listCriteria);
		return employeeRepository.count(predicate);
	}
	
	private BooleanExpression generatePredicate(List<SearchCriteria> criterias) throws Exception {
		if (criterias.isEmpty()) {
			return null;
		}

		BooleanExpression result = null;
		for (SearchCriteria criteria : criterias) {
			List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
			if (criteria.getFilters().isEmpty()) {
				return null;
			}
			for (SearchFilter filter : criteria.getFilters()) {
				final BooleanExpression exp = getSinglePredicate(filter);
				if (exp != null) {
					predicates.add(exp);
				}
			}
			BooleanExpression tmpResult = predicates.get(0);
			for (int i = 1; i < predicates.size(); i++) {
				if (criteria.getLogic().equalsIgnoreCase("or")) {
					result = tmpResult.or(predicates.get(i));
				} else if (criteria.getLogic().equalsIgnoreCase("and")) {
					result = tmpResult.and(predicates.get(i));
				} else {
					throw new Exception("logic not provided");
				}
			}
		}
		return result;
	}
	
	private BooleanExpression getSinglePredicate(SearchFilter filter) {
		PathBuilder<Employee> entityPath = new PathBuilder<Employee>(Employee.class, "employee");
		
		StringPath path = entityPath.getString(filter.getKey());
		String value = filter.getValue().toString();
		if (filter.getOperation().equalsIgnoreCase("eq")) {
			return path.eq(value);
		} else if (filter.getOperation().equalsIgnoreCase("icontains")) {
			return path.containsIgnoreCase(value);
		}
		return null;
	}

}
