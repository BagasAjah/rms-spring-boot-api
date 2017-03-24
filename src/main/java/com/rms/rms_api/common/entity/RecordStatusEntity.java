package com.rms.rms_api.common.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class RecordStatusEntity {

    @Column(name = "recordStatusID", nullable = false)
    protected int    recordStatusID;

	public int getRecordStatusID() {
		return recordStatusID;
	}

	public void setRecordStatusID(int recordStatusID) {
		this.recordStatusID = recordStatusID;
	}
    
}
