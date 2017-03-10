package com.rms.rms_api.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rms.rms_api.common.Response;
import com.rms.rms_api.employee.Employee;
import com.rms.rms_api.employee.service.EmployeeServiceImpl;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@CrossOrigin
	@RequestMapping(value = "api/employees")
	public List<Employee> getAll() {
		return employeeServiceImpl.getAllEmployees();
	}
	
	@CrossOrigin
	@RequestMapping(value = "api/employeesWithOutDetails")
	public List<Employee> getAllWithOutDetails() {
		return employeeServiceImpl.getAllEmployeesWithOutDetails();
	}
	
	@CrossOrigin
	@RequestMapping(value = "api/employee/{id}")
	public Employee getAllWithOutDetails(@PathVariable String id) {
		return employeeServiceImpl.getAllEmployeesById(id);
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, value = "api/employee")
	public Response save(@RequestBody Employee employee) {
		Response response = new Response();
		response.setData(employeeServiceImpl.saveOrUpdate(employee));
		return response;
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.PUT, value = "api/employee")
	public Response update(@RequestBody Employee employee) {
		Response response = new Response();
		response.setData(employeeServiceImpl.saveOrUpdate(employee));
		return response;
	}
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, value = "api/employee/{id}")
	public void delete(@PathVariable String id) {
		employeeServiceImpl.delete(id);
	}
}
