package com.achawan.view.employee;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.controller.MyController;
import com.achawan.employee.ApplyLeaves;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/form")
@PageTitle("Apply Leave Form")
public class LeaveRequestForm extends VerticalLayout {

	private static final String OH = "OH";
	private static final String SL = "SL";
	private static final String PTO = "PTO";

	@Autowired
	private MyController controller;

	private static final long serialVersionUID = 1L;

	private TextField empName = new TextField("Employee Name");
	private DatePicker fromDate = new DatePicker("From Date");
	private DatePicker tillDate = new DatePicker("Till Date");
	private Select<String> leavetype = new Select<String>();

	private Button apply = new Button("Apply");
	private Button cancel = new Button("Cancel");

	public LeaveRequestForm(MyController controller) {
		this.controller = controller;
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);

		configFields();
		configBtn();

		add(empName, leavetype, fromDate, tillDate, buttonLayout());
	}

	private void configBtn() {
		apply.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);

		apply.addClickShortcut(Key.ENTER);
		apply.addClickListener(e -> {
			if (leavetype.isEmpty()) {
				Notification.show("Leave type cannot be empty", 2000, Position.TOP_CENTER);
			} else {
				ApplyLeaves leaves = new ApplyLeaves(empName.getValue(), fromDate.getValue(), tillDate.getValue(),
						leavetype.getValue());
				String response = controller.applyLeave(leaves);

				getUI().get().navigate(EmployeeView.class);
				Notification.show(response, 2000, Position.TOP_CENTER);
			}
		});

		cancel.addClickListener(e -> {
			getUI().get().navigate(EmployeeView.class);
		});
	}

	private HorizontalLayout buttonLayout() {
		return new HorizontalLayout(apply, cancel);
	}

	private void configFields() {

		String value = controller.getEmployeeName();
		empName.setValue(value);
		empName.setEnabled(false);

		// config leaveType
		leavetype.setLabel("Leave Type");
		leavetype.setPlaceholder("Leave type");
		leavetype.setItems(Arrays.asList(PTO, SL, OH));

		// fromDate and toDate auto fill todays date
		fromDate.setValue(LocalDate.now());
		fromDate.addValueChangeListener(e -> {
			tillDate.setValue(e.getValue());
			tillDate.setMin(e.getValue());
		});
		tillDate.setValue(LocalDate.now());
		tillDate.addValueChangeListener(e -> fromDate.setMax(e.getValue()));
	}

}
