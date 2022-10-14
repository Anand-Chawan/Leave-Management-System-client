package com.achawan.employee;

public class Employee {

	private String id;

	private String firstName;

	private String lastName;

	private String userName;

	private String password;

	private String role;

	private String email;

	private Leaves leaves;

	public Employee() {

	}

	public Employee(Employee employee) {
		firstName = employee.firstName;
		lastName = employee.lastName;
		userName = employee.userName;
		password = employee.password;
		role = employee.role;
		email = employee.email;
		leaves = employee.leaves;
	}

	public Employee(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Leaves getLeaves() {
		return leaves;
	}

	public void setLeaves(Leaves leaves) {
		this.leaves = leaves;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName
				+ ", role=" + role + ", email=" + email + ", leaves=" + leaves + "]";
	}
	
	

}
