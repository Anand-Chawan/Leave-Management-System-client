package com.achawan.view.admin;

import com.achawan.common.LogoutBtn;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route("admin")
@RouteAlias("")
@PageTitle("Admin View")
public class AdminView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private Button EmpListBtn = new Button("Employee List", VaadinIcon.LIST.create());
	private Button LeavesReqBtn = new Button("Leave requests", VaadinIcon.RECORDS.create());

	public AdminView() {
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);

		configButtons();
		add(EmpListBtn, LeavesReqBtn, LogoutBtn.logoutBtn());

	}

	private void configButtons() {
		EmpListBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		LeavesReqBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		EmpListBtn.addClickListener(e -> {
			getUI().get().navigate(EmployeeList.class);
		});

		LeavesReqBtn.addClickListener(e -> {
			getUI().get().navigate(LeaveRequestList.class);
		});

	}
}
