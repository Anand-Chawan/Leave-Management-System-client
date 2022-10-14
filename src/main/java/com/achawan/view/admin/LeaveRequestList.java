package com.achawan.view.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.common.LogoutBtn;
import com.achawan.controller.MyController;
import com.achawan.employee.ApproveOrDiscardLeave;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("Leaverequests")
@PageTitle("Leave requests")
public class LeaveRequestList extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MyController controller;

	private Grid<ApproveOrDiscardLeave> grid = new Grid<>(ApproveOrDiscardLeave.class);
	private Button home = new Button("Home", VaadinIcon.HOME.create(),event -> {
		getUI().get().navigate(AdminView.class);
	});

	public LeaveRequestList(MyController controller) {
		this.controller = controller;

		setSizeFull();

		configGrid();

		updateGrid();

		add(getContent(), grid);
	}

	private HorizontalLayout getContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.add(new H1("Approve/Deny Pending Leaves"));
		layout.add(home, LogoutBtn.logoutBtn());

		layout.setAlignItems(Alignment.BASELINE);
		return layout;
	}

	private void updateGrid() {
		grid.setItems(controller.getAllPendingLeaves());

	}

	private void configGrid() {
		grid.removeAllColumns();
		grid.setColumns("username", "date", "leaveType");
		grid.getColumns().forEach(e -> e.setAutoWidth(true));

		grid.addColumn(new ComponentRenderer<>(Button::new, (button, employee) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
			button.setIcon(new Icon(VaadinIcon.CHECK));

			button.addClickListener(e -> {
				String response = controller.approveLeave(employee);
				Notification.show(response, 2000, Position.TOP_CENTER);
				reloadPage();
			});
		})).setHeader("Approve");

		grid.addColumn(new ComponentRenderer<>(Button::new, (button, employee) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
			button.setIcon(new Icon(VaadinIcon.TRASH));

			button.addClickListener(e -> {
				String response = controller.denyLeave(employee);
				Notification.show(response, 2000, Position.TOP_CENTER);
				reloadPage();
			});
		})).setHeader("Deny");

		grid.setWidth("100%");
	}

	private void reloadPage() {
		UI.getCurrent().getPage().reload();
	}
}
