package com.achawan.view.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.common.LogoutBtn;
import com.achawan.controller.MyController;
import com.achawan.employee.Employee;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("admin/listemployees")
@PageTitle("Admin CRUD View")
public class EmployeeList extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MyController controller;

	private TextField filter = new TextField();
	private Button addEmployeeBtn = new Button("Add Employee", VaadinIcon.USER.create());
	private Button home = new Button("Home", VaadinIcon.HOME.create(),event -> {
		getUI().get().navigate(AdminView.class);
	});
	private Grid<Employee> grid = new Grid<>(Employee.class);
	private EmployeeForm employeeForm;

	public EmployeeList(MyController controller) {
		this.controller = controller;
		setSizeFull();
		configGrid();
		configFilter();

		add(new HorizontalLayout(filter, addEmployeeBtn, home, LogoutBtn.logoutBtn()), getContent());
		updateGrid();
		updateBtn();

	}

	private HorizontalLayout getContent() {
		employeeForm = new EmployeeForm(controller);
		employeeForm.setVisible(false);
		employeeForm.setWidth("25em");

		HorizontalLayout layout = new HorizontalLayout(grid, employeeForm);
		layout.setSizeFull();
		return layout;
	}

	private void updateBtn() {
		home.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		
		addEmployeeBtn.addClickListener(e -> {
			employeeForm.setEmployee(new Employee());

			employeeForm.setVisible(true);
			employeeForm.setSaveBtnEnable();
			employeeForm.setUpdateBtnDisable();
		});
	}

	private void configGrid() {
		grid.setSizeFull();
		grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		grid.removeAllColumns();
		grid.setColumns("id", "firstName", "lastName", "userName", "email", "leaves");
		grid.getColumns().forEach(e -> e.setAutoWidth(true));

		grid.addItemClickListener(e -> {
			if (e.getClickCount() == 2) {
				// set employee form
				employeeForm.setEmployee(e.getItem());
				employeeForm.setUpdateBtnEnable();
				employeeForm.setSaveBtnDisable();
				employeeForm.setVisible(true);
			} else {
				employeeForm.setSaveBtnEnable();
				employeeForm.setUpdateBtnEnable();
				employeeForm.setVisible(false);
			}
		});
	}

	private void configFilter() {
		filter.setPrefixComponent(VaadinIcon.FILTER.create());
		filter.setPlaceholder("Filter...");
		filter.setClearButtonVisible(true);
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> {
			updateGrid();
		});
	}

	private void updateGrid() {
		if (filter == null || filter.isEmpty())
			grid.setItems(controller.getAllEmployees());
		else
			grid.setItems(controller.getAllEmployees_Filter(filter.getValue()));
	}

}
