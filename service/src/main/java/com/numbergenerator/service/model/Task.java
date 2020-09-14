package com.numbergenerator.service.model;

import java.io.Serializable;

public class Task implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4061755064281279798L;
	private String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Task [task=" + task + "]";
	}
	
	
}
