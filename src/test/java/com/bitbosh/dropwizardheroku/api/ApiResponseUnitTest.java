package com.bitbosh.DropwizardHeroku.api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ApiResponseUnitTest {

  @Test
  public void getEvents_returnsCorrectList_IfInjectedListNotNull() {
    List<Event> events = new ArrayList<Event>();
    String expectedName = "testName";
    String expectedLocation = "testLocation";
    String expectedDescription = "testDescription";
    Date date = new Date();

    events.add(new Event("testName", "testLocation", "testDescription", date));
    ApiResponse response = new ApiResponse(events);
    List<Event> actualList = (List<Event>) response.getList();

    String actualName = actualList.get(0).getName();
    String actualLocation = actualList.get(0).getLocation();
    String actualDescription = actualList.get(0).getDescription();

    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
  }

  @Test
  public void getEvents_returnsNull_IfInjectedListNull() {
    List<Event> expectedList = null;
    ApiResponse response = new ApiResponse(expectedList);
    List<Event> actualList = (List<Event>) response.getList();
    assertEquals(expectedList, actualList);
  }
}
