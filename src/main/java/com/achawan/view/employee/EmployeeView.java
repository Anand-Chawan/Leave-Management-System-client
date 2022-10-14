package com.achawan.view.employee;

import org.springframework.beans.factory.annotation.Autowired;

import com.achawan.common.LogoutBtn;
import com.achawan.controller.MyController;
import com.achawan.employee.Leaves;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("employee")
@PageTitle("LMS")
public class EmployeeView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MyController controller;
	
	private Paragraph leaveBalance = new Paragraph();
	private Button applyLeaveBtn = new Button("Apply Leave");
	private Button leavesStatusBtn = new Button("Leaves Status");

	public EmployeeView(MyController controller) {
		this.controller = controller;
		
		setSizeFull();
		setJustifyContentMode(JustifyContentMode.CENTER);
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);

		configBtn();
		configLeaveBalance();
		
		add(leaveBalance, applyLeaveBtn, leavesStatusBtn, LogoutBtn.logoutBtn());
	}

	private void configLeaveBalance() {
		leaveBalance.setTitle("Available Leaves");
		Leaves leaves = controller.getAvailableLeaves();
		leaveBalance.setText("PTO: " + leaves.getPTO() + "	SL: " + leaves.getSL() + "	OH: " + leaves.getOH());
	}

	private void configBtn() {
		applyLeaveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		leavesStatusBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		
		applyLeaveBtn.addClickListener(e -> {
			//TODO check validations and apply
			getUI().get().navigate(LeaveRequestForm.class);
		});
		
		leavesStatusBtn.addClickListener(e -> {
			getUI().get().navigate(LeaveReport.class);
		});
	}

}
