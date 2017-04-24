package com.bitbosh.DropwizardHeroku.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.skife.jdbi.v2.DBI;

import com.bitbosh.DropwizardHeroku.repository.EventDao;

import mockit.Expectations;
import mockit.Mocked;

public class EventResourceUnitTest {

  @Test
  public void EventResource_constructsSuccessfully_IfEventDaoNotNull(@Mocked DBI jdbi, @Mocked EventDao eventDao) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = eventDao; // return valid EventDao

        eventDao.createEventDatabaseTable();
        times = 1;
      }
    };

    EventResource eventResource = new EventResource(jdbi);
  }

  @Test(expected = NullPointerException.class)
  public void EventResource_throwsNullPointerExceptions_IfEventDaoNull(@Mocked DBI jdbi) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = null; // Do not return valid EventDao
      }
    };

    EventResource eventResource = new EventResource(jdbi);
  }

  @Test
  public void getEvent_expectedEventDaoMethodsInvoked_IfEventDaoNotNull(@Mocked DBI jdbi, @Mocked EventDao eventDao) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = eventDao;

        eventDao.createEventDatabaseTable();
        times = 1;

        eventDao.getEventByName(anyString);
        times = 1;
      }
    };

    EventResource eventResource = new EventResource(jdbi);

    Event event = eventResource.getEvent("test");
  }

  @Test(expected = NullPointerException.class)
  public void getEvent_throwsNullPointerException_IfEventDaoNull(@Mocked DBI jdbi) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = null;
      }
    };

    EventResource eventResource = new EventResource(jdbi);

    Event event = eventResource.getEvent("test");
  }

  @Test
  public void createEvent_expectedEventDaoMethodsInvoked_IfEventDaoNotNull(@Mocked DBI jdbi, @Mocked EventDao eventDao,
      @Mocked Event event) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = eventDao;

        eventDao.createEventDatabaseTable();
        times = 1;

        eventDao.createEvent(event.getName(), event.getLocation(), event.getLocation(), event.getDate());
        times = 1;
      }
    };

    EventResource eventResource = new EventResource(jdbi);
    eventResource.createEvent(event);
  }

  @Test(expected = NullPointerException.class)
  public void createEvent_throwsNullPointerException_IfEventDaoNull(@Mocked DBI jdbi, @Mocked Event event) {

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = null;
      }
    };

    EventResource eventResource = new EventResource(jdbi);
    eventResource.createEvent(event);
  }

  @Test
  public void getEvents_returnsEventList_IfEventListInjectedNotNull(@Mocked DBI jdbi, @Mocked EventDao eventDao) {

    List<Event> expectedList = new ArrayList<Event>();
    String expectedName = "testName";
    String expectedLocation = "testLocation";
    String expectedDescription = "testDescription";
    Date date = new Date();

    expectedList.add(new Event("testName", "testLocation", "testDescription", date));

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = eventDao;

        eventDao.createEventDatabaseTable();
        times = 1;

        eventDao.getEvents();
        result = expectedList;
        times = 1;
      }
    };

    EventResource eventResource = new EventResource(jdbi);
    ApiResponse response = eventResource.getEvents();
    List<Event> actualList = (List<Event>) response.getList();

    String actualName = actualList.get(0).getName();
    String actualLocation = actualList.get(0).getLocation();
    String actualDescription = actualList.get(0).getDescription();

    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
  }

  @Test
  public void getEvents_returnsNull_IfEventListInjectedNull(@Mocked DBI jdbi, @Mocked EventDao eventDao) {

    List<Event> expectedList = null;

    new Expectations() {
      {
        jdbi.onDemand(withAny(EventDao.class));
        result = eventDao;

        eventDao.createEventDatabaseTable();
        times = 1;

        eventDao.getEvents();
        result = expectedList;
        times = 1;
      }
    };

    EventResource eventResource = new EventResource(jdbi);
    ApiResponse response = eventResource.getEvents();
    List<Event> actualList = (List<Event>) response.getList();

    assertEquals(expectedList, actualList);
  }
}
