package com.rms.rms_api.common;

public class SearchFilter {

    private String key;
    private String operation;
    private String value;

    public SearchFilter() {

    }

    public SearchFilter(String key, String operation, String value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}

