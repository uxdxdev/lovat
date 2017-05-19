package com.bitbosh.dropwizardheroku.webgateway.api;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.repository.WebGatewayDao;
import com.bitbosh.dropwizardheroku.webgateway.views.IndexView;

import io.dropwizard.jersey.params.LongParam;

@Path("/")
public class WebGatewayResource {

	private final WebGatewayDao webGatewayDao;
	private final Client client;
	private NashornController nashornController;
	static final String kEventsServiceUrl = "https://dropwizardheroku-event-service.herokuapp.com";	
	static final String kEventsApiEndpointEvents = kEventsServiceUrl + "/v1/api/events";
	static final String kServerRenderFunction = "renderServer";

	public WebGatewayResource(DBI jdbi, Client client, NashornController nashornController) {
		this.webGatewayDao = jdbi.onDemand(WebGatewayDao.class);
		this.client = client;
		this.nashornController = nashornController;
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public IndexView index() throws IOException {

		// Get events json data from Events microservice
		ApiResponse events = getEventsJsonData();

		/*
		@SuppressWarnings("unused")
		String createEventFormComponent = this.nashornController.renderReactJsComponent(kRenderServerFunctionCreateEventFormUiComponent);
		
		// Render the Events List component and pass in props
		@SuppressWarnings("unchecked")
		List<Object> eventsProps = (List<Object>) events.getList();
		String eventsListComponent = this.nashornController.renderReactJsComponent(kRenderServerFunctionEventsListUiComponent, eventsProps);
		*/
		
		@SuppressWarnings("unchecked")
		List<Object> props = (List<Object>) events.getList();
		String indexViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunction, props);

		IndexView index = new IndexView(indexViewHtml);
		return index;
	}

	private ApiResponse getEventsJsonData() {
		WebTarget webTarget = this.client.target(kEventsApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		return response.readEntity(ApiResponse.class);
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getEvents() {
		WebTarget webTarget = this.client.target(kEventsApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		ApiResponse apiResponse = response.readEntity(ApiResponse.class);
		return apiResponse;
	}
	
	@POST
	@Path("/events")	
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResponse createEvent(String jsonObject) {
		WebTarget webTarget = this.client.target(kEventsApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(jsonObject, MediaType.APPLICATION_JSON));
		return response.readEntity(ApiResponse.class);		
	}
	
	@DELETE
	@Path("/events/{id}")		
	public ApiResponse deleteEventById(@PathParam("id") LongParam id) {
		WebTarget webTarget = this.client.target(kEventsApiEndpointEvents + "/" + id);		
		Response response = webTarget.request().delete();
		return response.readEntity(ApiResponse.class); 
	}
}
