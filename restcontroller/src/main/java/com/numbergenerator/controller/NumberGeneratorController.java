package com.numbergenerator.controller;

import java.util.Objects;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.numbergenerator.repository.model.Status;
import com.numbergenerator.service.impl.NumberGeneratorServiceImpl;
import com.numbergenerator.service.model.GeneratorRequest;
import com.numbergenerator.service.model.Result;
import com.numbergenerator.service.model.Task;
import com.numbergenerator.service.model.TaskResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * 
 * 
 * @author Vutukuri Sathvik.
 *
 */
@Api(value = "NumberGeneratorAPI", description = "Store/Retrieve Number generator information by UUID.")
@RestController
@RequestMapping("/api")
public class NumberGeneratorController {

	@Inject
	private NumberGeneratorServiceImpl numberGeneratorServiceImpl;

	@ApiOperation(value = "Gets the status of the task submitted.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success response"),
			@ApiResponse(code = 204, message = "UUID not found") })
	@ResponseBody
	@GetMapping(value = "/tasks/{uuid}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Result> fetchStatus(@PathVariable("uuid") String uuid) {

		Result result = new Result();

		Status status = numberGeneratorServiceImpl.getStatus(uuid);
		if (Objects.isNull(status)) {

			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} else {

			result.setResult(status.getValue());
			return ResponseEntity.status(HttpStatus.OK).body(result);

		}

	}

	@ApiOperation(value = "Creates the task information")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted the request.") })
	@ResponseBody
	@PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Task> generateFileOuput(@RequestBody @Valid GeneratorRequest generatorRequest) {

		Task task = numberGeneratorServiceImpl.postGeneratorRequest(generatorRequest);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(task);

	}

	@ApiOperation(value = "Gets the result of the task submitted.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success response"),
			@ApiResponse(code = 400, message = "UUID not found") })
	@ResponseBody
	@GetMapping("/tasks/{uuid}")
	public ResponseEntity<Result> getNumListResult(@PathVariable("uuid") String uuid,
			@RequestParam("action") String action) {

		if (action.equals("get_numlist")) {
			Result result = new Result();
			TaskResult taskResult = numberGeneratorServiceImpl.getTaskResult(uuid);
			if (taskResult.getStatus().equals(Status.SUCCESS)) {

				result.setResult(taskResult.getValue());
				return ResponseEntity.status(HttpStatus.OK).body(result);

			}

			else {

				result.setResult(taskResult.getValue());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}

		} else {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		}

	}

}
