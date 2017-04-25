package com.bitbosh.dropwizardheroku.webgateway.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.dropwizardheroku.webgateway.repository.WebGatewayDao;

@Path("/webgateway")
@Produces(MediaType.APPLICATION_JSON)
public class WebGatewayResource {

	private final WebGatewayDao webGatewayDao;

	public WebGatewayResource(DBI jdbi) {
		webGatewayDao = jdbi.onDemand(WebGatewayDao.class);
	}
}
