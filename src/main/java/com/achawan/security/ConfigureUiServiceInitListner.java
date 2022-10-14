package com.achawan.security;

import org.springframework.stereotype.Component;

import com.achawan.controller.MyController;
import com.achawan.view.admin.AdminView;
import com.achawan.view.admin.EmployeeList;
import com.achawan.view.admin.LeaveRequestList;
import com.achawan.view.employee.EmployeeView;
import com.achawan.view.employee.LeaveReport;
import com.achawan.view.employee.LeaveRequestForm;
import com.achawan.view.login.ForgotPassword;
import com.achawan.view.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component
public class ConfigureUiServiceInitListner implements VaadinServiceInitListener {

	private static final String ADMIN = "admin";
	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::beforeEnter);
		});
	}

	private void beforeEnter(BeforeEnterEvent event) {
		// if not logged in, redirect to login page, except forgot password
		if (!ForgotPassword.class.equals(event.getNavigationTarget())
				&& !LoginView.class.equals(event.getNavigationTarget()) && !SecurityUtils.IsUserLoggedIn()) {
			event.rerouteTo(LoginView.class);
		}
		// if already logged in, and trying to access login page, then redirect to
		// home page
		else if (SecurityUtils.IsUserLoggedIn() && LoginView.class.equals(event.getNavigationTarget())) {
			if (MyController.loggedInUserName.equals(ADMIN)) {
				event.rerouteTo(AdminView.class);
			} else {
				event.rerouteTo(EmployeeView.class);
			}
		}
		// if employee logged in, and trying to access admin page, reroute to employee
		// view
		else if (SecurityUtils.IsUserLoggedIn() && !MyController.loggedInUserName.equals(ADMIN)) {
			final boolean isTryingToAccessAdminPage = event.getNavigationTarget().equals(AdminView.class)
					|| event.getNavigationTarget().equals(EmployeeList.class)
					|| event.getNavigationTarget().equals(LeaveRequestList.class);
			if (isTryingToAccessAdminPage) {
				event.rerouteTo(EmployeeView.class);
			}
		}
		// if Admin logged in, and trying to access employee page, reroute to admin view
		else if (SecurityUtils.IsUserLoggedIn() && MyController.loggedInUserName.equals(ADMIN)) {
			final boolean isTryingToAccessEmployeePage = event.getNavigationTarget().equals(EmployeeView.class)
					|| event.getNavigationTarget().equals(LeaveReport.class)
					|| event.getNavigationTarget().equals(LeaveRequestForm.class);
			if (isTryingToAccessEmployeePage) {
				event.rerouteTo(AdminView.class);
			}
		}
	}

}
