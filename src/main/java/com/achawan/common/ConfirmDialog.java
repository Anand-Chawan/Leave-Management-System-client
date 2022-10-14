package com.achawan.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public final class ConfirmDialog extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private String areYouSure = "Are you sure?";
	public static final Button YES = new Button("YES");
	public static final Button NO = new Button("NO");

	public ConfirmDialog() {
		setSizeFull();
		add(new H5(areYouSure), buttonLayout());
	}

	private HorizontalLayout buttonLayout() {
		YES.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		NO.addThemeVariants(ButtonVariant.LUMO_ERROR);

		return new HorizontalLayout(YES, NO);
	}
}
