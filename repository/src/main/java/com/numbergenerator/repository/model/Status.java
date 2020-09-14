package com.numbergenerator.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
	
	
	SUCCESS(0,"SUCCESS"),
	IN_PROGRESS(1,"IN_PROGRESS"),
	ERROR(2,"ERROR"),
	NEW(3,"NEW");

	
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;
	
	Status(int id, String value) {

		this.id = id;
		this.value = value;
	
	}

	
	
	

}
