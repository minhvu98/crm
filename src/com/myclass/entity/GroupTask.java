package com.myclass.entity;

import java.util.List;

public class GroupTask {
	private String fullname;
	private List<Task> tasks;
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
