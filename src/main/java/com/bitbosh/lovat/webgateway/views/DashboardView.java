package com.bitbosh.lovat.webgateway.views;

import io.dropwizard.views.View;

public class DashboardView extends View {
	
	private final String dashboardViewHtml;	
	
	public DashboardView(String dashboardViewHtml) {
		super("Dashboard.mustache");
		this.dashboardViewHtml = dashboardViewHtml;
	}

	public String getDashbaordViewHtml() {
		return dashboardViewHtml;
	}		
}
