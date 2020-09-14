package com.numbergenerator.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "task_detail")
@DynamicInsert
public class TaskDetail {

	@Id
	@SequenceGenerator(name = "ID_GENERATOR", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "goal")
	private Integer goal;

	@Column(name = "step")
	private Integer step;

	@Column(name = "filepath")
	private String filePath;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "row_created", nullable = false)
	private Date rowCreated;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "row_updated", nullable = false)
	private Date rowUpdated;

	@Column(name = "status_id")
	@Enumerated(EnumType.ORDINAL)
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Date getRowCreated() {
		return rowCreated;
	}

	public void setRowCreated(Date rowCreated) {
		this.rowCreated = rowCreated;
	}

	public Date getRowUpdated() {
		return rowUpdated;
	}

	public void setRowUpdated(Date rowUpdated) {
		this.rowUpdated = rowUpdated;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "TaskDetail [id=" + id + ", uuid=" + uuid + ", goal=" + goal + ", step=" + step + ", filePath="
				+ filePath + ", rowCreated=" + rowCreated + ", rowUpdated=" + rowUpdated + ", status=" + status + "]";
	}

}
