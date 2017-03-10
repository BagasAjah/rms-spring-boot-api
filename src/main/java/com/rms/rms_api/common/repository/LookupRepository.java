package com.rms.rms_api.common.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rms.rms_api.common.Lookup;

public interface LookupRepository extends PagingAndSortingRepository <Lookup, String>{
	
	public List<Lookup> findByLookupType(String type);
}
