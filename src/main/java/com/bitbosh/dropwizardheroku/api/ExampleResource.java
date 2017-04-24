package com.bitbosh.DropwizardHeroku.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/example")
public class ExampleResource {

  public ExampleResource() {
  }

  @GET
  @Path("/hello")
  public String hello() {
    return "Hello\n";
  }

  @GET
  @Path("/query")
  public String query(@QueryParam("message") String message) {
    return "You passed " + message + "\n";
  }

  @POST
  @Path("/postbody")
  public String postBody(String message) {
    return "You posted " + message + "\n";
  }

  @POST
  @Path("/postparam")
  public String postParam(@FormParam("message") String message) {
    return "You posted " + message + "\n";
  }
}