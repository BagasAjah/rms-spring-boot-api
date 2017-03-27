package com.rms.rms_api.employee.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.rms.rms_api.common.RMSConstant;
import com.rms.rms_api.common.SearchCriteria;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.repository.EmployeeRepository;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeFamilyServiceImpl employeeFamilyServiceImpl;

	@Autowired
	private EmployeeGradeServiceImpl employeeGradeServiceImpl;

	@Autowired
	private EmployeeHistoryServiceImpl employeeHistoryServiceImpl;

	@Autowired
	private EmployeeLocationServiceImpl employeeLocationServiceImpl;

	@Override
	public List<Employee> getAllEmployees(List<SearchCriteria> criterias, Pageable pageable) throws Exception {
		List<Employee> employees = new ArrayList<>();
		em.unwrap(Session.class).enableFilter(RMSConstant.ACTIVE_FILTER);
		if (criterias.isEmpty() && pageable.getSort() == null) {
			employeeRepository.findAllByOrderByFirstNameAsc(pageable).forEach(employees::add);
		} else {
			BooleanExpression predicate = generatePredicate(criterias);
			employeeRepository.findAll(predicate, pageable).forEach(employees::add);
		}
		employees.get(0).setBase64Image(convertByteToBase64(employees.get(0).getAvatar()));
		return employees;
	}

	@Override
	public List<Employee> getAllEmployeesWithOutDetails(List<SearchCriteria> criterias, Pageable pageable)
			throws Exception {
		List<Employee> employees = new ArrayList<>();
		em.unwrap(Session.class).enableFilter(RMSConstant.ACTIVE_FILTER);
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
		employees.get(0).setBase64Image(convertByteToBase64(employees.get(0).getAvatar()));
		return employees;
	}

	@Override
	public Employee getEmployeeById(String guid) {
		Employee employee = new Employee();
		employee = findByID(guid);
		employee.setBase64Image(convertByteToBase64(employee.getAvatar()));
		return employee;
	}

	@Override
	public String save(Employee updatedEmployee) {
		updatedEmployee.getGradeHistory().forEach(grade -> {
			grade.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
			grade.setEmployee(updatedEmployee);
		});
		updatedEmployee.getFamilyMember().forEach(family -> {
			family.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
			family.setEmployee(updatedEmployee);
		});
		updatedEmployee.getLocation().forEach(location -> {
			location.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
			location.setEmployee(updatedEmployee);
		});
		updatedEmployee.getHistory().forEach(history -> {
			history.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE);
			history.setEmployee(updatedEmployee);
			history.getJobDescList().forEach(jobdesc -> jobdesc.setRecordStatusID(RMSConstant.RECORD_STATUS_ACTIVE));
		});
		return saveOrUpdate(updatedEmployee);
	}

	@Override
	public String update(Employee updatedEmployee) throws Exception {
		Employee existingEmployee = findByID(updatedEmployee.getEmployeeGuid());
		if (existingEmployee == null) {
			throw new Exception("Employees not found");
		}

		existingEmployee = employeeFamilyServiceImpl.mergeEmployeeFamily(existingEmployee, updatedEmployee);
		existingEmployee = employeeGradeServiceImpl.mergeEmployeeGrade(existingEmployee, updatedEmployee);
		existingEmployee = employeeHistoryServiceImpl.mergeEmployeeHistory(existingEmployee, updatedEmployee);
		existingEmployee = employeeLocationServiceImpl.mergeEmployeeLocation(existingEmployee, updatedEmployee);

		existingEmployee.setAvatar(convertBase64ToByte(updatedEmployee.getBase64Image()));

		return saveOrUpdate(existingEmployee);
	}

	@Override
	public Employee findByID(String guid) {
		em.unwrap(Session.class).enableFilter(RMSConstant.ACTIVE_FILTER);
		return employeeRepository.findOne(guid);
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

	private String saveOrUpdate(Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee.getEmployeeGuid();
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
			if (filterObj.containsKey(RMSConstant.FILTER_CRITERIA_LOGIC)) {
				result = constructFilterObject(result, entityPath, predicate, filterObj, criteria.getLogic());
			} else {
				result = constructPredicate(result, entityPath, predicate, filterObj, criteria.getLogic());
			}
		}
		return result;
	}

	private BooleanExpression constructFilterObject(BooleanExpression result, PathBuilder<Employee> entityPath,
			BooleanExpression predicate, JSONObject filterObj, String criteriaLogic) throws Exception {
		String logic = filterObj.getString(RMSConstant.FILTER_CRITERIA_LOGIC);
		JSONArray filtersArray = filterObj.getJSONArray(RMSConstant.FILTER_CRITERIA_FILTERS);
		BooleanExpression nestedPredicate = null;
		for (int j = 0; j < filtersArray.size(); j++) {
			JSONObject nestedFilterObj = filtersArray.getJSONObject(j);
			nestedPredicate = constructPredicate(nestedPredicate, entityPath, predicate, nestedFilterObj, logic);
		}

		if (result == null) {
			result = nestedPredicate;
		} else {
			if (criteriaLogic.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_OPERATION_OR)) {
				result = result.or(nestedPredicate);
			} else if (criteriaLogic.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_OPERATION_AND)) {
				result = result.and(nestedPredicate);
			} else {
				throw new Exception("logic not provided");
			}
		}

		return result;
	}

	private BooleanExpression constructPredicate(BooleanExpression result, PathBuilder<Employee> entityPath,
			BooleanExpression predicate, JSONObject filterObj, String logic) throws Exception {
		String key = filterObj.getString(RMSConstant.FILTER_CRITERIA_FIELD);
		String operation = filterObj.getString(RMSConstant.FILTER_CRITERIA_OPERATION);
		String value = filterObj.getString(RMSConstant.FILTER_CRITERIA_VALUE);

		if (key.equalsIgnoreCase("name")) {
			StringExpression concate = entityPath.getString("firstName").concat(" ")
					.concat(entityPath.getString("lastName"));
			predicate = concate.containsIgnoreCase(value);
		} else {
			StringPath path = entityPath.getString(key);
			if (operation.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_LOGIC_EQ)) {
				predicate = path.eq(value);
			} else if (operation.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_LOGIC_LIKE)) {
				predicate = path.containsIgnoreCase(value);
			}
		}

		if (predicate == null) {
			return result;
		}

		if (result == null) {
			result = predicate;
		} else {
			if (logic.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_OPERATION_OR)) {
				result = result.or(predicate);
			} else if (logic.equalsIgnoreCase(RMSConstant.FILTER_CRITERIA_OPERATION_AND)) {
				result = result.and(predicate);
			} else {
				throw new Exception("logic not provided");
			}
		}
		return result;
	}

	private byte[] convertBase64ToByte(String base64Image) {
		if (StringUtils.isBlank(base64Image)) {
			return null;
		}
		base64Image = base64Image.replace("\"", "");
		String[] imageCodeArray = base64Image.split(",");
		return Base64.getDecoder().decode(imageCodeArray[1]);
	}

	private String convertByteToBase64(byte[] input) {
		if (input == null) {
			return null;
		}
		String encoded = Base64.getEncoder().encodeToString(input);
		StringBuilder builder = new StringBuilder();
		builder.append("data:image/png;base64," + encoded);
		return builder.toString();
	}

}
