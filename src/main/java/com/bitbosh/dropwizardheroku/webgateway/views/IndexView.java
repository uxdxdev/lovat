package com.bitbosh.dropwizardheroku.webgateway.views;

import java.util.List;

import io.dropwizard.views.View;

public class IndexView extends View {
	
	private final String eventsComponent;
	private List<Object> eventsProps;
	
	public IndexView(String eventsComponent, List<Object> eventsProps) {
		super("Index.mustache");
		this.eventsComponent = eventsComponent;
		this.eventsProps = eventsProps;
	}

	public String getEventsComponent() {
		return eventsComponent;
	}

	public List<Object> getEventsList() {
		return eventsProps;
	}	
}
