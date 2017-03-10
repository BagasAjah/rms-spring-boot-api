package com.rms.rms_api.common.service;

import java.util.List;

import com.rms.rms_api.common.Lookup;

public interface LookupService {
	public List<Lookup> getByType (String type);
}
