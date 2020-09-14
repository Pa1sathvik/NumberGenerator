package com.numbergenerator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static org.mockito.Mockito.doThrow;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.numbergenerator.repository.GeneratorRepository;
import com.numbergenerator.repository.model.Status;
import com.numbergenerator.repository.model.TaskDetail;
import com.numbergenerator.service.BaseModuelTest;
import com.numbergenerator.service.model.GeneratorRequest;
import com.numbergenerator.service.util.ServiceConstants;
import com.numbergenerator.service.util.UniqueIdGenerator;

@ContextConfiguration(classes = NumberGeneratorServiceImpl.class)
public class NumberGeneratorServiceImplTest extends BaseModuelTest {

	@MockBean
	private GeneratorRepository generatorRepository;

	@InjectMocks
	private NumberGeneratorServiceImpl numberGeneratorServiceImpl;

	@MockBean
	private UniqueIdGenerator uniqueIdGenerator;

	/**
	 * Method for prerequisite conditions
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetStatus() {

		when(generatorRepository.findByUuid(any(String.class))).thenReturn(null);
		assertEquals(null, numberGeneratorServiceImpl.getStatus("11-229"));

	}

	@Test
	public void testGetStatusNew() {

		TaskDetail task = new TaskDetail();
		task.setStatus(Status.NEW);
		when(generatorRepository.findByUuid(any(String.class))).thenReturn(task);
		assertEquals(Status.IN_PROGRESS, numberGeneratorServiceImpl.getStatus("11-229"));

	}

	@Test
	public void testGetStatusInProgress() {

		TaskDetail task = new TaskDetail();
		task.setStatus(Status.IN_PROGRESS);
		when(generatorRepository.findByUuid(any(String.class))).thenReturn(task);
		assertEquals(Status.IN_PROGRESS, numberGeneratorServiceImpl.getStatus("11-229"));

	}

	@Test
	public void testpostGeneratorRequest() {

		GeneratorRequest generatorRequest = new GeneratorRequest();
		generatorRequest.setGoal(12);
		generatorRequest.setStep(12);
		when(uniqueIdGenerator.generateId()).thenReturn("123902-20-");
		assertEquals("123902-20-", numberGeneratorServiceImpl.postGeneratorRequest(generatorRequest).getTask());

	}

	@Test
	public void testGetTaskResultNull() {

		when(generatorRepository.findByUuid(any(String.class))).thenReturn(null);
		assertEquals(Status.ERROR, numberGeneratorServiceImpl.getTaskResult("123").getStatus());

	}

	@Test
	public void testGetTaskResultNoFile() {

		TaskDetail task = new TaskDetail();
		task.setStatus(Status.IN_PROGRESS);
		task.setFilePath("/tmp/ioi");

		when(generatorRepository.findByUuid(any(String.class))).thenReturn(task);
		assertEquals(Status.ERROR, numberGeneratorServiceImpl.getTaskResult("123").getStatus());

	}

	@Test
	public void testGetTaskResult() throws IOException {

		File file = new File("/tmp/output.txt");
		FileWriter fWriter = new FileWriter(file.getAbsolutePath());
		PrintWriter printWriter = new PrintWriter(fWriter);
		int goal = 10;
		while (goal >= 0) {
			printWriter.write(String.valueOf(goal) + ServiceConstants.NEW_LINE);
			goal = goal - 2;
		}
		printWriter.close();

		TaskDetail task = new TaskDetail();
		task.setStatus(Status.IN_PROGRESS);
		task.setFilePath(file.getAbsolutePath());

		when(generatorRepository.findByUuid(any(String.class))).thenReturn(task);
		assertEquals(Status.SUCCESS, numberGeneratorServiceImpl.getTaskResult("123").getStatus());

	}

	@Test
	public void testProcessTaskNull() {

		when(generatorRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

	}

	@Test
	public void testProcessTask() {

		TaskDetail task = new TaskDetail();
		task.setStatus(Status.IN_PROGRESS);
		task.setFilePath("/tmp/ui.txt");
		task.setGoal(10);
		task.setStep(2);

		when(generatorRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(task));
		when(generatorRepository.save(any(TaskDetail.class))).thenReturn(task);
		numberGeneratorServiceImpl.processTask(123L);

	}

	@Test
	public void testProcessTaskException() {

		TaskDetail task = new TaskDetail();
		
		task.setFilePath("///");
		task.setGoal(10);
		task.setStep(2);
		when(generatorRepository.save(any(TaskDetail.class))).thenReturn(task);
		when(generatorRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(task));
		numberGeneratorServiceImpl.processTask(123L);

	}

}
