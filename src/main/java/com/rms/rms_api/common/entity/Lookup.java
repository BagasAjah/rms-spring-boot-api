package com.rms.rms_api.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lookup {
	
	@Id
	@Column(name = "lookupGuid", length=36)
	private String lookupGuid;

	@Column(name = "lookupCode", length=36)
	private String lookupCode;

	@Column(name = "lookupType", length=36)
	private String lookupType;

	@Column(name = "lookupValue", length=36)
	private String lookupValue;

	public String getLookupGuid() {
		return lookupGuid;
	}

	public void setLookupGuid(String lookupGuid) {
		this.lookupGuid = lookupGuid;
	}

	public String getLookupCode() {
		return lookupCode;
	}

	public void setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	public String getLookupValue() {
		return lookupValue;
	}

	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}

}
