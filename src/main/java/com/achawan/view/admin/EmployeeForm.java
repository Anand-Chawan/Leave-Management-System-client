package com.achawan.view.admin;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.common.ConfirmDialog;
import com.achawan.controller.MyController;
import com.achawan.employee.Employee;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class EmployeeForm extends FormLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	private MyController controller;

	private TextField id = new TextField("ID");
	private TextField firstName = new TextField("First Name");
	private TextField lastName = new TextField("Last Name");
	private TextField userName = new TextField("Username");
	private EmailField email = new EmailField("Email");

	private Button saveBtn = new Button("Save");
	private Button updateBtn = new Button("Update");
	private Button deleteBtn = new Button("Delete");

	private Binder<Employee> binder = new Binder<Employee>(Employee.class);
	private Employee employee;

	public EmployeeForm(MyController controller) {
		this.controller = controller;
		setSizeFull();

		configFields();
		configButtons();
		binder.bindInstanceFields(this);
		add(id, firstName, lastName, userName, email, ButtonLayout());

	}

	private HorizontalLayout ButtonLayout() {
		return new HorizontalLayout(saveBtn, updateBtn, deleteBtn);
	}

	private void configButtons() {
		saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		updateBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
		deleteBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

		saveBtn.addClickListener(e -> {
			employee = new Employee();
			fillEmployeeDetails(employee);
			if (!firstName.isInvalid() && !lastName.isInvalid()) {
				if (!email.isEmpty() && email.isInvalid()) {
					Notification.show("Invalid Email", 2000, Position.TOP_CENTER);
				}
				controller.createEmployee(employee);
			}
			reloadPage();
		});

		updateBtn.addClickListener(e -> {
			employee = new Employee();
			fillEmployeeDetails(employee);

			if (validate()) {
				controller.updateEmployee(employee);
				reloadPage();
				Notification.show("Updated Successfully", 2000, Position.TOP_CENTER);
			} else {
				Notification.show("Please provide proper input", 2000, Position.TOP_CENTER);
			}
		});

		deleteBtn.addClickListener(e -> {
			Dialog dialog = new Dialog(new ConfirmDialog());
			dialog.open();

			ConfirmDialog.YES.addClickListener(event -> {
				controller.deleteEmployee(id.getValue());
				dialog.close();
				reloadPage();
			});

			ConfirmDialog.NO.addClickListener(event -> dialog.close());
		});

	}

	private void reloadPage() {
		this.setVisible(false); // CRUD should hide
		UI.getCurrent().getPage().reload();
	}

	private boolean validate() {
		if (!firstName.isInvalid() && !lastName.isInvalid() && !email.isInvalid())
			return true;
		return false;
	}

	private void fillEmployeeDetails(Employee employee) {

		try {
			binder.writeBean(employee);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void configFields() {
		id.setEnabled(false);

		firstName.setRequired(true);
		firstName.setClearButtonVisible(true);

		lastName.setRequired(true);
		lastName.setClearButtonVisible(true);

		userName.setClearButtonVisible(true);
		email.setClearButtonVisible(true);
		email.setErrorMessage("Enter Valid Email");
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		binder.readBean(this.employee);
	}

	public void setUpdateBtnDisable() {
		updateBtn.setEnabled(false);
	}

	public void setUpdateBtnEnable() {
		updateBtn.setEnabled(true);
	}

	public void setSaveBtnDisable() {
		saveBtn.setEnabled(false);
	}

	public void setSaveBtnEnable() {
		saveBtn.setEnabled(true);
	}
}
