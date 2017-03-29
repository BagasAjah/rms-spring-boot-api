package com.rms.rms_api.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rms.rms_api.common.entity.Lookup;
import com.rms.rms_api.common.repository.LookupRepository;

@Service
public class LookupServiceImpl implements LookupService {
	
	@Autowired
	private LookupRepository lookupRepository;

	@Override
	public List<Lookup> getByType(String type) {
		List<Lookup> lookups = new ArrayList<>();
		lookupRepository.findByLookupType(type).forEach(lookups::add);
		return lookups;
	}

}
