package com.achawan.view.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.controller.MyController;
import com.achawan.employee.Login;
import com.achawan.view.admin.AdminView;
import com.achawan.view.employee.EmployeeView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("login")
@PageTitle("Login | Leave Management System")
public class LoginView extends VerticalLayout {

	@Autowired
	private MyController controller;

	private static final long serialVersionUID = 1L;
	private TextField username = new TextField("Username");
	private PasswordField password = new PasswordField("Password");
	private Button loginBtn = new Button("login", VaadinIcon.SIGN_IN.create());
	private RouterLink forgotPassword = new RouterLink("forgot Password?", ForgotPassword.class);

	public LoginView(MyController controller) {
		this.controller = controller;
		
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);

		configFields();
		configButton();

		add(username, password, loginBtn, forgotPassword);
	}

	private void configFields() {
		username.setPlaceholder("achawan");
		username.setSuffixComponent(VaadinIcon.USER.create());
		username.setClearButtonVisible(true);
		username.setRequired(true);

		password.setPlaceholder("password");
		password.setClearButtonVisible(true);
		password.setRequired(true);
	}

	private void configButton() {
		loginBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		loginBtn.addClickShortcut(Key.ENTER);

		loginBtn.addClickListener(e -> {
			if (username.isEmpty() || password.isEmpty()) {
				Notification.show("Username or Password cannot be empty", 2000, Position.TOP_CENTER);
			} else {
				ValidateUserAndLogin();
			}
		});
	}

	private void ValidateUserAndLogin() {
		// create login user
		Login user = new Login(username.getValue(), password.getValue());

		// check user credentials
		if (controller.AuthenticateUser(user)) {

			if (username.getValue().equals("admin")) {
				getUI().get().navigate(AdminView.class);
			} else {
				getUI().get().navigate(EmployeeView.class);
			}
			
		} else {
			Notification.show("Invalid Credentials", 2000, Position.TOP_CENTER);
		}
	}
}
