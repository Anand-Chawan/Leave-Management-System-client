package com.achawan.common;

import com.achawan.view.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.server.VaadinSession;

public final class LogoutBtn {

	private LogoutBtn() {
	}

	public static Button logoutBtn() {
		Button logout = new Button("Logout", VaadinIcon.SIGN_OUT.create());
		logout.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);

		logout.addClickListener(e -> {
			VaadinSession.getCurrent().close();
			UI.getCurrent().navigate(LoginView.class);
		});

		return logout;
	}
}
