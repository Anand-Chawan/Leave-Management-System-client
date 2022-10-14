package com.achawan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.achawan.employee.ApplyLeaves;
import com.achawan.employee.ApproveOrDiscardLeave;
import com.achawan.employee.Employee;
import com.achawan.employee.LeaveStatus;
import com.achawan.employee.Leaves;
import com.achawan.employee.Login;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.server.VaadinSession;

import reactor.core.publisher.Mono;

@Component
public final class MyController {

	public static final String REJECTED = "Rejected";
	public static final String PENDING = "Pending";
	public static final String APPROVED = "Approved";
	private String cookie;
	public static String loggedInUserName;

	private static final String COOKIE_NAME = "lmsApp";
	private static final String BASE_URL = "http://localhost:8080/api";
	private static final String LOGIN = "/login";
	private static final String CREATE = "/create";
	private static final String GET_ALL_EMPLOYEES = "/getAll";
	private static final String GET_ALL_EMPLOYEES_BY_FILTER = "/getAll/";
	private static final String GET_BY_ID = "/getbyid/";
	private static final String GET_BY_USERNAME = "/getbyusername/";
	private static final String GET_BY_EMAIL = "/getbyemail/";
	private static final String UPDATE = "/update";
	private static final String DELETE = "/delete/";
	private static final String GET_ALL_PENDING_LEAVES = "/allpendingleaves";
	private static final String APPROVE_LEAVES = "/approveleave";
	private static final String DENY_LEAVES = "/denyleave";
	private static final String APPLY_LEAVES = "/applyleave";
	private static final String DISCARD_LEAVES = "/discardleave";
	private static final String AVAILABLE_LEAVES = "/availableleaves";
	private static final String GET_EMPLOYEE_NAME = "/getemployeename";

	private WebClient webClient;

	public MyController() {
		this.webClient = WebClient.create(BASE_URL);
	}

	public boolean AuthenticateUser(Login user) {
		try {
			cookie = webClient.post().uri(LOGIN).body(Mono.just(user), Login.class).retrieve().bodyToMono(String.class)
					.block();

			// parse cookie "lmsApp=" has length 7
			// parse before ';'
			cookie = (cookie.substring(7, cookie.indexOf(";")));
		} catch (Exception e) {
			VaadinSession.getCurrent().setAttribute(String.class, null);
			return false;
		}
		VaadinSession.getCurrent().setAttribute(String.class, cookie);
		loggedInUserName = user.getUsername();
		return true;
	}

//	################### ADMIN ########################
	public List<Employee> getAllEmployees() {
		return webClient.get().uri(GET_ALL_EMPLOYEES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToFlux(Employee.class).collect(Collectors.toList()).block();
	}

	public String getCookie() {
		return cookie;
	}

	public List<Employee> getAllEmployees_Filter(String filter) {
		return webClient.get().uri(GET_ALL_EMPLOYEES_BY_FILTER + filter)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToFlux(Employee.class).collect(Collectors.toList()).block();
	}

	public Employee getEmployeeById(String id) {
		return webClient.get().uri(GET_BY_ID + id)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(Employee.class).block();
	}

	public Employee getEmployeeByUsername(String username) {
		return webClient.get().uri(GET_BY_USERNAME + username)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(Employee.class).block();
	}

	public Employee getEmployeeByEmail(String email) {
		return webClient.get().uri(GET_BY_EMAIL + email)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(Employee.class).block();
	}

	public Employee createEmployee(Employee employee) {
		Employee emp = null;
		try {
			emp = webClient.post().uri(CREATE).body(Mono.just(employee), Employee.class)
					.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
					.bodyToMono(Employee.class).block();
		} catch (Exception e) {
			Notification.show("Some error occured while creating employee", 2000, Position.TOP_CENTER);
		}

		return emp;
	}

	public Employee updateEmployee(Employee employee) {
		Employee emp = null;
		try {
			emp = webClient.post().uri(UPDATE).body(Mono.just(employee), Employee.class)
					.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
					.bodyToMono(Employee.class).block();
		} catch (Exception e) {
			Notification.show("Some error occured while updating employee", 2000, Position.TOP_CENTER);
		}

		return emp;
	}

	public String deleteEmployee(String id) {
		String res = null;
		try {
			res = webClient.delete().uri(DELETE + id)
					.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
					.bodyToMono(String.class).block();
		} catch (Exception e) {
			Notification.show("Some error occured while deleting employee", 2000, Position.TOP_CENTER);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<ApproveOrDiscardLeave> getAllPendingLeaves() {
		List<ApproveOrDiscardLeave> pendingLeavesList = new ArrayList<>();

		HashMap<String, HashMap<String, String>> res = webClient.get().uri(GET_ALL_PENDING_LEAVES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(HashMap.class).block();

		res.forEach((user, leave) -> {
			leave.forEach((date, leaveType) -> {
				pendingLeavesList.add(new ApproveOrDiscardLeave(user, date, leaveType));
			});
		});

		return pendingLeavesList;
	}

	public String denyLeave(ApproveOrDiscardLeave leave) {
		return webClient.post().uri(DENY_LEAVES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class))
				.body(Mono.just(leave), ApproveOrDiscardLeave.class).retrieve().bodyToMono(String.class).block();
	}

	public String approveLeave(ApproveOrDiscardLeave leave) {
		return webClient.post().uri(APPROVE_LEAVES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class))
				.body(Mono.just(leave), ApproveOrDiscardLeave.class).retrieve().bodyToMono(String.class).block();
	}
//	##################### END ADMIN ########################

//	##################### USER ###############################
	public String applyLeave(ApplyLeaves leaves) {
		String res;
		try {
			res = webClient.post().uri(APPLY_LEAVES)
					.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class))
					.body(Mono.just(leaves), ApplyLeaves.class).retrieve().bodyToMono(String.class).block();
		} catch (Exception e) {
			res = "Some error occured";
		}
		return res;
	}

	public String discardLeave(ApproveOrDiscardLeave discardLeave) {
		return webClient.post().uri(DISCARD_LEAVES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class))
				.body(Mono.just(discardLeave), ApproveOrDiscardLeave.class).retrieve().bodyToMono(String.class).block();
	}

	public List<LeaveStatus> getLeaveStatus() {
		Leaves leaves = getAvailableLeaves();

		List<LeaveStatus> leaveStatusList = new ArrayList<>();

		leaves.getApprovedLeaves().forEach((date, leaveType) -> {
			leaveStatusList.add(new LeaveStatus(date, leaveType, APPROVED));
		});

		leaves.getPendingLeaves().forEach((date, leaveType) -> {
			leaveStatusList.add(new LeaveStatus(date, leaveType, PENDING));
		});

		leaves.getRejectedLeaves().forEach((date, leaveType) -> {
			leaveStatusList.add(new LeaveStatus(date, leaveType, REJECTED));
		});
		
		return leaveStatusList;
	}

	public String getEmployeeName() {
		return webClient.get().uri(GET_EMPLOYEE_NAME)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(String.class).block();
	}

	public Leaves getAvailableLeaves() {
		return webClient.get().uri(AVAILABLE_LEAVES)
				.cookie(COOKIE_NAME, VaadinSession.getCurrent().getAttribute(String.class)).retrieve()
				.bodyToMono(Leaves.class).block();
	}

}
