package com.numbergenerator.service;

import org.springframework.stereotype.Service;

import com.numbergenerator.repository.model.Status;
import com.numbergenerator.service.model.GeneratorRequest;
import com.numbergenerator.service.model.Task;
import com.numbergenerator.service.model.TaskResult;

@Service
public interface NumberGeneratorService {
	
	
	
	
	public Task postGeneratorRequest(GeneratorRequest generatorRequest);

	public Status getStatus(String uuid);

	public TaskResult getTaskResult(String uuid);

	public void processTask(Long taskDetailId);
	
	

}
