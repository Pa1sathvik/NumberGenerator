package com.numbergenerator.service.util;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.numbergenerator.repository.GeneratorRepository;
import com.numbergenerator.repository.model.Status;
import com.numbergenerator.repository.model.TaskDetail;
import com.numbergenerator.service.scheduler.AbstractTask;
import com.numbergenerator.service.scheduler.AnalysisTask;

@Named
public class ServiceMethods {

	@Inject
	private GeneratorRepository generatorRepository;

	public void handleException(List<AbstractTask> abstractTaskList, int i) {

		Long taskDetailsId = ((AnalysisTask)abstractTaskList.get(i)).getTaskDetailId();
		TaskDetail taskDetail = generatorRepository.getOne(taskDetailsId);
		taskDetail.setStatus(Status.ERROR);
		generatorRepository.save(taskDetail);

	}

}
