package com.rms.rms_api.common;

public class ResponseWrapper {
	
	private Object result;
	
	private Object total;
	
	public ResponseWrapper () {
	}
	
	public ResponseWrapper (Object result, Object total) {
		this.result = result;
		this.total = total;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getTotal() {
		return total;
	}

	public void setTotal(Object total) {
		this.total = total;
	}

}
