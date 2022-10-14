package com.achawan.view.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("forgot")
@PageTitle("Reset Password")
public class ForgotPassword extends VerticalLayout{

	private static final long serialVersionUID = 1L;

	public ForgotPassword() {
		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
		add(new H1("LOL! Ask your admin to reset password"));
	}
}
