package com.rms.rms_api.common;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchCriteria {
	private String logic;
	private JSONArray filters;

	public SearchCriteria() {

	}

	public SearchCriteria(JSONObject jsonFilter, String logic) {
		super();
		JSONArray filterArray = jsonFilter.getJSONArray("filters");
		this.logic = logic;
		this.filters = filterArray;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public JSONArray getFilters() {
		return filters;
	}

	public void setFilters(JSONArray filters) {
		this.filters = filters;
	}
}
