package com.myclass.entity;

public class StatusJob {
	private int id;
	private String statusId;
	private String name;
	private String countId;

	
	public StatusJob() {}

	
	public StatusJob(int id, String statusId, String name, String countId, int total) {
		super();
		this.id = id;
		this.statusId = statusId;
		this.name = name;
		this.countId = countId;

	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountId() {
		return countId;
	}

	public void setCountId(String countId) {
		this.countId = countId;
	}


	
}
