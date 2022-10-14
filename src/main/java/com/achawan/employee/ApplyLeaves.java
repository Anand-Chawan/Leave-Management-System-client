package com.achawan.employee;

import java.time.LocalDate;

public class ApplyLeaves {

	private String empName;

	private LocalDate fromDate;

	private LocalDate tillDate;

	private String leaveType;

	public ApplyLeaves(String empName, LocalDate fromDate, LocalDate tillDate, String leaveType) {
		super();
		this.empName = empName;
		this.fromDate = fromDate;
		this.tillDate = tillDate;
		this.leaveType = leaveType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getTillDate() {
		return tillDate;
	}

	public void setTillDate(LocalDate tillDate) {
		this.tillDate = tillDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Override
	public String toString() {
		return "ApplyLeaves [empName=" + empName + ", fromDate=" + fromDate + ", tillDate=" + tillDate + ", leaveType="
				+ leaveType + "]";
	}

}
