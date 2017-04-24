package com.bitbosh.DropwizardHeroku.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.skife.jdbi.v2.DBI;

import com.bitbosh.DropwizardHeroku.repository.EventDao;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

  private final EventDao eventDao;

  public EventResource(DBI jdbi) {
    eventDao = jdbi.onDemand(EventDao.class);
    eventDao.createEventDatabaseTable();
  }

  @GET
  public ApiResponse getEvents() {
    List<Event> eventList = eventDao.getEvents();
    ApiResponse response = new ApiResponse(eventList);
    return response;
  }

  @GET
  @Path("/{name}")
  public Event getEvent(@PathParam("name") String name) {
    Event event = eventDao.getEventByName(name);
    return event;
  }

  @POST
  public void createEvent(Event event) {
    eventDao.createEvent(event.getName(), event.getLocation(), event.getLocation(), event.getDate());
  }
}
