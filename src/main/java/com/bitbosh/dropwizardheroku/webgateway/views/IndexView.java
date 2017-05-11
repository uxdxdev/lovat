package com.bitbosh.dropwizardheroku.webgateway.views;

import io.dropwizard.views.View;

public class IndexView extends View {
	
	private final String createEventFormComponent;
	private final String eventsListComponent;
	
	public IndexView(String createEventFormComponent, String eventsListComponent) {
		super("Index.mustache");
		this.createEventFormComponent = createEventFormComponent;
		this.eventsListComponent = eventsListComponent;
	}	

	public String getCreateEventFormComponent() {
		return createEventFormComponent;
	}
	
	public String getEventsListComponent() {
		return eventsListComponent;
	}
}
