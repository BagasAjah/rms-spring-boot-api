package com.rms.rms_api.common;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SearchCriteria {
	private String logic;
	private List<SearchFilter> filters;

	public SearchCriteria() {

	}

	public SearchCriteria(JSONObject jsonFilter, String logic) {
		super();
		JSONArray filterArray = jsonFilter.getJSONArray("filters");
		this.logic = logic;
		this.filters = new ArrayList<>();
		for (int i = 0; i < filterArray.size(); i++) {
			JSONObject filterobj = filterArray.getJSONObject(i);
			String key = filterobj.getString("field");
			String operation = filterobj.getString("operator");
			String value = filterobj.getString("value");
			this.filters.add(new SearchFilter(key, operation, value));
		}
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<SearchFilter> filters) {
		this.filters = filters;
	}
}
