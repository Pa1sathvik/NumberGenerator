package com.numbergenerator.service.scheduler;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.numbergenerator.repository.GeneratorRepository;
import com.numbergenerator.repository.model.Status;
import com.numbergenerator.repository.model.TaskDetail;
import com.numbergenerator.service.util.ServiceMethods;

@Service
@PropertySource(value = "classpath:config.properties")
public class NumberGeneratorSchedule {

	private static Logger logger = LoggerFactory.getLogger(NumberGeneratorSchedule.class);

	@Inject
	private GeneratorRepository generatorRepository;

	private ThreadPoolHandler threadPoolHandler;

	@Inject
	private ApplicationContext applicationContext;

	@Value("${scheduler.pool.size}")
	private int threadPoolSize;

	@Value("${scheduler.pool.timeOut}")
	private int threadTimeoutValue;

	@Inject
	private ServiceMethods serviceMethods;

	@PostConstruct
	public void setConfig() {
		threadPoolHandler = applicationContext.getBean(ThreadPoolHandler.class, threadPoolSize, threadTimeoutValue);
	}

	// Polls db with a delay of 5 seconds to get the tasks of status "NEW" and proceeds
	// for file output task.
	@Scheduled(fixedDelayString = "5000")
	public void processTasks() {

		List<TaskDetail> taskdetailsList = generatorRepository.findByStatus(Status.NEW);

		if (taskdetailsList.isEmpty()) {

			return;
		}
		List<AbstractTask> abstractTaskList = taskdetailsList.stream().map(task -> {

			AnalysisTask analysisTask = applicationContext.getBean(AnalysisTask.class);
			analysisTask.setTaskDetailId(task.getId());
			return analysisTask;

		}).collect(Collectors.toList());

		List<Future<Void>> taskStatus = threadPoolHandler.invokeAll(abstractTaskList);

		for (int i = 0; i < taskStatus.size(); i++) {
			Future<Void> futureResult = taskStatus.get(i);
			try {
				futureResult.get();
			} catch (Exception e) {
				serviceMethods.handleException(abstractTaskList, i);
			}

		}
	}
}
