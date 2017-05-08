package com.bitbosh.dropwizardheroku.webgateway.views;

import io.dropwizard.views.View;

public class IndexView extends View {
	
	private final String eventsComponent;
	
	public IndexView(String eventsComponent) {
		super("Index.mustache");
		this.eventsComponent = eventsComponent;
	}

	public String getEventsComponent() {
		return eventsComponent;
	}	
}
