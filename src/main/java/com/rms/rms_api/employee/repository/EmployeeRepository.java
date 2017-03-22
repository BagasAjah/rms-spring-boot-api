package com.rms.rms_api.employee.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rms.rms_api.employee.Employee;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, String>, QueryDslPredicateExecutor<Employee> {
	
	List<Employee> findAllByOrderByFirstNameAsc(Pageable pageable);

}
