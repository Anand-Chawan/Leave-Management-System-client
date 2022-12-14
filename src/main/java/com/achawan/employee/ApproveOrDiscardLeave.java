package com.achawan.employee;

import java.time.LocalDate;

public class ApproveOrDiscardLeave {

	private String username;
	
	private LocalDate date;

	private String leaveType;
	
	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public ApproveOrDiscardLeave() {
		// TODO Auto-generated constructor stub
	}
	
	public ApproveOrDiscardLeave(String username, String date, String leaveType) {
		this.username = username;
		this.date = LocalDate.parse(date);
		this.leaveType = leaveType;
	}



	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
