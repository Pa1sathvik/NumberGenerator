package com.numbergenerator.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.numbergenerator.repository.GeneratorRepository;
import com.numbergenerator.repository.model.Status;
import com.numbergenerator.repository.model.TaskDetail;
import com.numbergenerator.service.NumberGeneratorService;
import com.numbergenerator.service.model.GeneratorRequest;
import com.numbergenerator.service.model.Task;
import com.numbergenerator.service.model.TaskResult;
import com.numbergenerator.service.util.ServiceConstants;
import com.numbergenerator.service.util.UniqueIdGenerator;

@Named
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

	@Inject
	private GeneratorRepository generatorRepository;

	@Inject
	private UniqueIdGenerator uniqueIdGenerator;

	private Logger logger = LoggerFactory.getLogger(NumberGeneratorServiceImpl.class);

	@Override
	public Status getStatus(String uuid) {

		TaskDetail taskDetail = generatorRepository.findByUuid(uuid);
		if (Objects.isNull(taskDetail)) {

			return null;
		}

		/*
		 * Status we are keeping as 'NEW' for our internal tracking of tasks. If status
		 * is NEW , we are sending it as "IN_PROGRESS" t0 end user.
		 */
		if (taskDetail.getStatus().equals(Status.NEW)) {

			return Status.IN_PROGRESS;
		}

		return taskDetail.getStatus();
	}

	@Override
	public Task postGeneratorRequest(GeneratorRequest generatorRequest) {

		Task task = new Task();
		TaskDetail taskDetail = new TaskDetail();
		taskDetail.setGoal(generatorRequest.getGoal());
		taskDetail.setStep(generatorRequest.getStep());
		String uniqueId = uniqueIdGenerator.generateId();
		taskDetail.setUuid(uniqueId);
		taskDetail.setStatus(Status.NEW);
		taskDetail.setFilePath(ServiceConstants.TMP_DIRECTORY + uniqueId + ServiceConstants.TXT_FILE_NAME_SUFFIX);
		generatorRepository.save(taskDetail);
		task.setTask(uniqueId);
		return task;

	}

	@Override
	public TaskResult getTaskResult(String uuid) {

		TaskResult taskResult = new TaskResult();
		TaskDetail taskDetail = generatorRepository.findByUuid(uuid);
		if (Objects.isNull(taskDetail)) {

			taskResult.setStatus(Status.ERROR);
			taskResult.setValue("Incorrect uuid passed");
			return taskResult;
		}
		StringBuilder sb = new StringBuilder();
		try (Scanner sc = new Scanner(new File(taskDetail.getFilePath()))) {

			while (sc.hasNextInt()) {
				sb.append(sc.nextInt()).append(ServiceConstants.COMMA);
			}

		} catch (FileNotFoundException e) {

			taskResult.setStatus(Status.ERROR);
			taskResult.setValue("Unable to process.Please try again.");
			return taskResult;
			
		}

		String result = null;

		if (sb.length() > 0) {

			result = sb.deleteCharAt(sb.length() - 1).toString();

		}
		taskResult.setStatus(Status.SUCCESS);

		taskResult.setValue(result);
		return taskResult;

	}

	@Override
	public void processTask(Long taskDetailId) {

		Optional<TaskDetail> taskDetailOptional = generatorRepository.findById(taskDetailId);
		if (taskDetailOptional.isPresent()) {

			TaskDetail taskDetail = taskDetailOptional.get();
			taskDetail.setStatus(Status.IN_PROGRESS);
			taskDetail = generatorRepository.save(taskDetail);
			try {
				writeToFile(taskDetail);
			} catch (IOException e) {

				taskDetail.setStatus(Status.ERROR);
				generatorRepository.save(taskDetail);
			}
		}

	}

	/**
	 * 
	 * Writes output to file.
	 * 
	 * @param taskDetail
	 * @throws IOException
	 */
	public void writeToFile(TaskDetail taskDetail) throws IOException {

		FileWriter fWriter = new FileWriter(taskDetail.getFilePath());
		PrintWriter printWriter = new PrintWriter(fWriter);

		int goal = taskDetail.getGoal();
		while (goal >= 0) {
			printWriter.write(String.valueOf(goal) + ServiceConstants.NEW_LINE);
			goal = goal - taskDetail.getStep();
		}
		printWriter.close();
		taskDetail.setStatus(Status.SUCCESS);
		generatorRepository.save(taskDetail);

	}

}
