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
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.EmployeeHistory;
import com.rms.rms_api.employee.repository.EmployeeRepository;
import com.rms.rms_api.employee.repository.JobDescHistoryRepository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
			if (criteria.getFilters().isEmpty()) {
				return null;
			}
			result = constructPredicate(result, criteria);
		}
		return result;
	}

	private BooleanExpression constructPredicate(BooleanExpression result, SearchCriteria criteria) throws Exception {
		for (int i = 0; i < criteria.getFilters().size(); i++) {
			PathBuilder<Employee> entityPath = new PathBuilder<Employee>(Employee.class, "employee");
			BooleanExpression predicate = null;
			JSONObject filterObj = criteria.getFilters().getJSONObject(i);
			if (filterObj.containsKey("logic")) {
				result = constructFilterObject(result, entityPath, predicate, filterObj, criteria.getLogic());
			} else {
				result = constructPredicate(result, entityPath, predicate, filterObj, criteria.getLogic());
			}
		}
		return result;
	}

	private BooleanExpression constructFilterObject(BooleanExpression result, PathBuilder<Employee> entityPath,
			BooleanExpression predicate, JSONObject filterObj, String criteriaLogic) throws Exception {
		String logic = filterObj.getString("logic");
		JSONArray filtersArray = filterObj.getJSONArray("filters");
		BooleanExpression nestedPredicate = null;
		for (int j = 0; j < filtersArray.size(); j++) {
			JSONObject nestedFilterObj = filtersArray.getJSONObject(j);
			nestedPredicate = constructPredicate(nestedPredicate, entityPath, predicate, nestedFilterObj, logic);
		}
		
		if (result == null) {
			result = nestedPredicate;
		} else {
			if (criteriaLogic.equalsIgnoreCase("or")) {
				result = result.or(nestedPredicate);
			} else if (criteriaLogic.equalsIgnoreCase("and")) {
				result = result.and(nestedPredicate);
			} else {
				throw new Exception("logic not provided");
			}
		}
		
		return result;
	}

	private BooleanExpression constructPredicate(BooleanExpression result, PathBuilder<Employee> entityPath,
			BooleanExpression predicate, JSONObject filterObj, String logic) throws Exception {
		String key = filterObj.getString("field");
		String operation = filterObj.getString("operator");
		String value = filterObj.getString("value");
		
		StringPath path = entityPath.getString(key);
		if (operation.equalsIgnoreCase("eq")) {
			predicate = path.eq(value);
		} else if (operation.equalsIgnoreCase("icontains")) {
			predicate = path.containsIgnoreCase(value);
		}

		if (predicate == null) {
			return result;
		}
		
		if (result == null) {
			result = predicate;
		} else {
			if (logic.equalsIgnoreCase("or")) {
				result = result.or(predicate);
			} else if (logic.equalsIgnoreCase("and")) {
				result = result.and(predicate);
			} else {
				throw new Exception("logic not provided");
			}
		}
		return result;
	}

}
