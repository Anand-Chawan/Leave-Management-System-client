package com.achawan.employee;

import java.time.LocalDate;

public class LeaveStatus {

	private LocalDate date;
	
	private String leaveType;
	
	private String status;

	public LeaveStatus(LocalDate date, String leaveType, String status) {
		super();
		this.date = date;
		this.leaveType = leaveType;
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
