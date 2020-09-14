package com.numbergenerator.service.util;


import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.fasterxml.uuid.Generators;

/**
 * Generates Unique Id
 * 
 * @author Vutukuri Sathvik.
 *
 */
@Named
@Scope("singleton")
public class UniqueIdGenerator {
	
	/**
	 * Make sure not to add any member variable in class other than Atomic Integer
	 */
	private AtomicInteger atomicInteger = new AtomicInteger();
	
	/**
	 * Returns Unique Id based on timeStamp and AtomicInteger
	 * @return String
	 */
	public String generateId() {
		
		UUID uuid = Generators.timeBasedGenerator().generate();
		int value = atomicInteger.incrementAndGet();
		String id = uuid.toString() + value;
		return id;
	}
	
}

