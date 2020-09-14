package com.numbergenerator.service.model;

import java.io.Serializable;

public class Result implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1628411024619571791L;
	
	@Override
	public String toString() {
		return "Result [result=" + result + "]";
	}

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}
