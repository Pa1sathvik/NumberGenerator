package com.numbergenerator.service.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * 
 * @author Vutukuri Sathvik.
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class GeneratorRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5102840971265327750L;
	
	@NotNull
	@Size(min = 1 , message = "Goal should be minimum of 1")
	private int goal;
	
	@NotNull
	@Size(min = 1 , message = "Step should be minimum of 1")
	private int step;

	
	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "GeneratorRequest [goal=" + goal + ", step=" + step + "]";
	}

}
