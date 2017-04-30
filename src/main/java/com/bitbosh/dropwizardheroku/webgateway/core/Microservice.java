package com.bitbosh.dropwizardheroku.webgateway.core;

public class Microservice {

	public static final String kEventsServiceUrl = "https://dropwizardheroku-event-service.herokuapp.com";
	public static final String kEventsApiEndpointEvents = kEventsServiceUrl + "/v1/api/events";
	public static final String kEventsUiComponentUrl = kEventsServiceUrl + "/js/events.js";
	public static final String kEventsUiComponentRenderServerFunction = "renderServerEvents";
}
