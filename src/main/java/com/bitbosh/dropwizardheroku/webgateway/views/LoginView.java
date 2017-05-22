package com.bitbosh.dropwizardheroku.webgateway.views;

import io.dropwizard.views.View;

public class LoginView extends View {
	
	private final String loginViewHtml;	
	
	public LoginView(String loginViewHtml) {
		super("Login.mustache");
		this.loginViewHtml = loginViewHtml;
	}

	public String getLoginViewHtml() {
		return loginViewHtml;
	}		
}
