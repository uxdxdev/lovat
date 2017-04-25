package com.bitbosh.dropwizardheroku.webgateway.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.repository.WebGatewayDao;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WebGatewayResource {

	private final WebGatewayDao webGatewayDao;
	private final Client client;

	public WebGatewayResource(DBI jdbi, Client client) {
		this.webGatewayDao = jdbi.onDemand(WebGatewayDao.class);
		this.client = client;
	}

	@GET
	@Path("/events")
	public ApiResponse getEvents() {
		//WebTarget webTarget = client.target("https://dropwizardheroku-event-service.herokuapp.com/v1/api/events");
		WebTarget webTarget = client.target("http://localhost:8081/v1/api/events");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        ApiResponse apiResponse = response.readEntity(ApiResponse.class);
        return apiResponse;
	}
}
