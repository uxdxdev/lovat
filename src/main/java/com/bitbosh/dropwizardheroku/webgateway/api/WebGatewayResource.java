package com.bitbosh.dropwizardheroku.webgateway.api;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.DBI;

@Path("/webgateway")
@Produces(MediaType.APPLICATION_JSON)
public class WebGatewayResource {

  public WebGatewayResource(DBI jdbi) {
  }
}
