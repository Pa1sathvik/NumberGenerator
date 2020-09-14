package com.numbergenerator.service.scheduler;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;


@Named
@Scope("prototype")
public class ThreadPoolHandler {

	private int size;

	private long timoutVal;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getTimoutVal() {
		return timoutVal;
	}

	public void setTimoutVal(long timoutVal) {
		this.timoutVal = timoutVal;
	}

	private ExecutorService service;

	private static Logger log = LoggerFactory.getLogger(ThreadPoolHandler.class);

	/**
	 * 
	 */
	public ThreadPoolHandler(int size, long timeout) {
		service = Executors.newFixedThreadPool(size);
		this.timoutVal = timeout;
	}

	/**
	 * @param tasks
	 * @return
	 */
	public List<Future<Void>> invokeAll(List<AbstractTask> tasks) {
		List<Future<Void>> futureList = null;
		try {
			futureList = service.invokeAll(tasks, timoutVal, TimeUnit.MINUTES);
		} catch (Exception e) {

			log.error("Something avengers level threat--", e);
			
		}
		return futureList;
	}

}

