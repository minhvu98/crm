package com.myclass.dto;

public class CountJobUser {
	private String statusname;
	private String count;
	
	public CountJobUser() {}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public CountJobUser(String statusname, String count) {
		super();
		this.statusname = statusname;
		this.count = count;
	}
	
}
