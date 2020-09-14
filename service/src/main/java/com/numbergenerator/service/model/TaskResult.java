package com.numbergenerator.service.model;

import com.numbergenerator.repository.model.Status;

public class TaskResult {

	
	private Status status;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "TaskResult [status=" + status + ", value=" + value + "]";
	}
	private String value;
	
}
