package com.numbergenerator.service.scheduler;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.numbergenerator.service.NumberGeneratorService;

@Named
@Scope("prototype")
public class AnalysisTask extends AbstractTask {

	private Long taskDetailId;

	public Long getTaskDetailId() {
		return taskDetailId;
	}

	public void setTaskDetailId(Long taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public AnalysisTask() {

	}

	public AnalysisTask(Long taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	@Inject
	private NumberGeneratorService numberGeneratorService;

	@Override
	public Void call() throws Exception {

		try {
			numberGeneratorService.processTask(taskDetailId);

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}

}
