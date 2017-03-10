package com.rms.rms_api.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rms.rms_api.common.Lookup;
import com.rms.rms_api.common.service.LookupServiceImpl;

@RestController
public class LookupController {
	
	@Autowired
	private LookupServiceImpl 	lookupServiceImpl;
	
	@CrossOrigin
	@RequestMapping(value = "api/lookup/{type}")
	public List<Lookup> getAll(@PathVariable String type) {
		return lookupServiceImpl.getByType(type);
	}
}
