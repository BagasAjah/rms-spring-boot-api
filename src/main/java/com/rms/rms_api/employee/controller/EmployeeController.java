package com.rms.rms_api.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rms.rms_api.common.SearchCriteria;
import com.rms.rms_api.common.Response;
import com.rms.rms_api.common.ResponseWrapper;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.service.EmployeeServiceImpl;

import net.sf.json.JSONObject;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@CrossOrigin
	@RequestMapping(value = "api/employees")
	public ResponseEntity<ResponseWrapper> getAll(@RequestParam String search, @RequestParam String filter, Pageable pageable) throws Exception {
		List<SearchCriteria> listCriteria = new ArrayList<>();
		JSONObject jsonSearch = new JSONObject();
		JSONObject jsonFilter = new JSONObject();
		if (!search.isEmpty()) {
			jsonSearch = JSONObject.fromObject(search);
		}
		if (!jsonSearch.isEmpty()) {
			listCriteria.add(new SearchCriteria(jsonSearch, "or"));
		}
		if (!filter.isEmpty()) {
			jsonFilter = JSONObject.fromObject(filter);
		}
		if (!jsonFilter.isEmpty()) {
			listCriteria.add(new SearchCriteria(jsonFilter, "or"));
		}

		List<Employee> result = employeeServiceImpl.getAllEmployees(listCriteria, pageable);
		Long total = employeeServiceImpl.getTotalEmployee(listCriteria);
		ResponseWrapper wrapper = new ResponseWrapper(result, total);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "api/employeesWithOutDetails")
	public ResponseEntity<ResponseWrapper> getAllWithOutDetails(@RequestParam String search, Pageable pageable) throws Exception {
		List<SearchCriteria> listCriteria = new ArrayList<>();
		JSONObject jsonFilter = new JSONObject();
		jsonFilter = JSONObject.fromObject(search);
		if (!jsonFilter.isEmpty()) {
			listCriteria.add(new SearchCriteria(jsonFilter, "or"));
		}
		List<Employee> result =  employeeServiceImpl.getAllEmployeesWithOutDetails(listCriteria, pageable);
		Long total = employeeServiceImpl.getTotalEmployee(listCriteria);
		ResponseWrapper wrapper = new ResponseWrapper(result, total);
		return new ResponseEntity<>(wrapper, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "api/employee/{id}")
	public ResponseEntity<Employee> getEmployeeWithOutDetails(@PathVariable String id) {
		Employee employee =  employeeServiceImpl.getEmployeeById(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "api/employee")
	public ResponseEntity<Response> save(@RequestBody Employee employee) {
		String guid = employeeServiceImpl.saveOrUpdate(employee);
		Response repsonse = new Response(guid);
		return new ResponseEntity<>(repsonse, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "api/employee")
	public ResponseEntity<Response> update(@RequestBody Employee employee) {
		String guid = employeeServiceImpl.saveOrUpdate(employee);
		Response repsonse = new Response(guid);
		return new ResponseEntity<>(repsonse, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "api/employee/{id}")
	public void delete(@PathVariable String id) {
		employeeServiceImpl.delete(id);
	}
}
