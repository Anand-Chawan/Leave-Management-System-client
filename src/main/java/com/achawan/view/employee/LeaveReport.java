package com.achawan.view.employee;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.common.LogoutBtn;
import com.achawan.controller.MyController;
import com.achawan.employee.ApproveOrDiscardLeave;
import com.achawan.employee.LeaveStatus;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("report")
@PageTitle("Leave Status")
public class LeaveReport extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	@Autowired
	private MyController controller;
	private Button home = new Button("Home", VaadinIcon.HOME.create(), event -> {
		getUI().get().navigate(EmployeeView.class);
	});
	private Grid<LeaveStatus> grid = new Grid<>(LeaveStatus.class);

	public LeaveReport(MyController controller) {
		this.controller = controller;
		setSizeFull();

		configGrid();

		add(getContent(), grid);

		updateGrid();
	}

	private HorizontalLayout getContent() {
		HorizontalLayout layout = new HorizontalLayout(home, LogoutBtn.logoutBtn());
		home.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		return layout;
	}

	private void configGrid() {
		grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
		grid.setSizeFull();
		grid.removeAllColumns();

		grid.addColumn(LeaveStatus::getDate).setHeader("Date");
		grid.addColumn(LeaveStatus::getLeaveType).setHeader("Leave Type");
		grid.addColumn(LeaveStatus::getStatus).setHeader("Status");
		grid.addColumn(new ComponentRenderer<>(Button::new, (button, employee) -> {
			button.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
			button.setIcon(new Icon(VaadinIcon.TRASH));
			button.setEnabled(employee.getStatus().equals(MyController.PENDING));

			button.addClickListener(e -> {
				String response = controller.discardLeave(
						new ApproveOrDiscardLeave(null, employee.getDate().toString(), employee.getLeaveType()));
				reloadPage();
				Notification.show(response, 2000, Position.TOP_CENTER);
			});
		})).setHeader("Discard");
	}

	private void reloadPage() {
		UI.getCurrent().getPage().reload();
	}

	private void updateGrid() {
		grid.setItems(controller.getLeaveStatus());
	}

}
