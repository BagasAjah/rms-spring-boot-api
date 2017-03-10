package com.rms.rms_api.employee.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rms.rms_api.employee.JobDescList;

public interface JobDescHistoryRepository extends PagingAndSortingRepository<JobDescList, String>{

	List<JobDescList> findByEmployeeHistoryGuid(String guid);
}
