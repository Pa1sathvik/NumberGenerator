package com.numbergenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.numbergenerator.repository.model.Status;
import com.numbergenerator.repository.model.TaskDetail;


@Repository
public interface GeneratorRepository extends JpaRepository<TaskDetail, Long>{

	
    public TaskDetail findByUuid(String uuid);	
    
    public List<TaskDetail> findByStatus(Status status);
	
}
