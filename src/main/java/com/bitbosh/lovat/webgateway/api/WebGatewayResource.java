package com.bitbosh.lovat.webgateway.api;

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

import com.bitbosh.lovat.webgateway.core.User;
import com.bitbosh.lovat.webgateway.repository.WebGatewayDao;
import com.bitbosh.lovat.webgateway.views.DashboardView;
import com.bitbosh.lovat.webgateway.views.LoginView;

import io.dropwizard.jersey.params.LongParam;

@Path("/")
public class WebGatewayResource {

	private final WebGatewayDao webGatewayDao;

	private final Client client;

	private NashornController nashornController;

	private final String kServerRenderFunctionDashboard = "renderServerDashboard";
	private final String kServerRenderFunctionLogin = "renderServerLogin";

	static final String kUserServiceUrl = "https://dropwizardheroku-user-service.herokuapp.com";
	static final String kUserServiceApiEndpointRegister = kUserServiceUrl + "/v1/api/users";

	static final String kEventServiceUrl = "https://dropwizardheroku-event-service.herokuapp.com";
	static final String kEventServiceApiEndpointEvents = kEventServiceUrl + "/v1/api/events";

	public WebGatewayResource(DBI jdbi, Client client, NashornController nashornController) {
		this.webGatewayDao = jdbi.onDemand(WebGatewayDao.class);
		this.client = client;
		this.nashornController = nashornController;
	}

	@GET	
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	public LoginView login() {
		String loginViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionLogin);
		LoginView login = new LoginView(loginViewHtml);
		return login;
	}
	
	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticate(User user) {	
		// authenticate user with User service		
		String username = user.getName();
		String password = user.getPassword();
		return Response.status(Response.Status.OK).build();
	}

	@GET	
	@Produces(MediaType.TEXT_HTML)
	public DashboardView dashboard() {		
		// Get events json data from Events microservice
		ApiResponse events = getEventsJsonData();

		@SuppressWarnings("unchecked")
		List<Object> props = (List<Object>) events.getList();
		String indexViewHtml = this.nashornController.renderReactJsComponent(kServerRenderFunctionDashboard, props);

		DashboardView index = new DashboardView(indexViewHtml);
		return index;
	}

	private ApiResponse getEventsJsonData() {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		return response.readEntity(ApiResponse.class);
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getEvents() {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		ApiResponse apiResponse = response.readEntity(ApiResponse.class);
		return apiResponse;
	}

	@POST
	@Path("/events")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResponse createEvent(String jsonObject) {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(jsonObject, MediaType.APPLICATION_JSON));
		return response.readEntity(ApiResponse.class);
	}

	@DELETE
	@Path("/events/{id}")
	public ApiResponse deleteEventById(@PathParam("id") LongParam id) {
		WebTarget webTarget = this.client.target(kEventServiceApiEndpointEvents + "/" + id);
		Response response = webTarget.request().delete();
		return response.readEntity(ApiResponse.class);
	}
}
