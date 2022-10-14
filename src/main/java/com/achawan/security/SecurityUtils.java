package com.achawan.security;

import javax.servlet.http.HttpServletRequest;

import com.vaadin.flow.server.VaadinSession;

public final class SecurityUtils {
	
	public static boolean IsUserLoggedIn(HttpServletRequest request) {
		try {
			VaadinSession.getCurrent().getAttribute(String.class).toString();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean IsUserLoggedIn() {
		return IsUserLoggedIn(null);
	}
}
