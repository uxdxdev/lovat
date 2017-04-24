package com.bitbosh.DropwizardHeroku.api;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class EventUnitTest {

  @Test
  public void Event_constructedSuccessfully_IfDefaultCtorCalled() {
    Event event = new Event();
    int expectedId = 0;
    String expectedName = "";
    String expectedLocation = "";
    String expectedDescription = "";
    Date expectedDate = new Date();

    int actualId = event.getId();
    String actualName = event.getName();
    String actualLocation = event.getLocation();
    String actualDescription = event.getDescription();
    Date actualDate = event.getDate();

    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
    // assertEquals(expectedDate, actualDate);
  }

  @Test
  public void Event_constructedSuccessfully_IfCorrectDataPassedToCtorWithId() {

    int expectedId = 1234;
    String expectedName = "testName";
    String expectedLocation = "testLocation";
    String expectedDescription = "testDescription";
    Date expectedDate = new Date();

    Event event = new Event(expectedId, expectedName, expectedLocation, expectedDescription, expectedDate);

    int actualId = event.getId();
    String actualName = event.getName();
    String actualLocation = event.getLocation();
    String actualDescription = event.getDescription();
    Date actualDate = event.getDate();

    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
    assertEquals(expectedDate, actualDate);
  }

  @Test
  public void Event_constructedSuccessfully_IfCorrectDataPassedToCtorWithoutId() {

    int expectedId = 0;
    String expectedName = "testName";
    String expectedLocation = "testLocation";
    String expectedDescription = "testDescription";
    Date expectedDate = new Date();

    Event event = new Event(expectedName, expectedLocation, expectedDescription, expectedDate);

    int actualId = event.getId();
    String actualName = event.getName();
    String actualLocation = event.getLocation();
    String actualDescription = event.getDescription();
    Date actualDate = event.getDate();

    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
    assertEquals(expectedDate, actualDate);
  }

  @Test
  public void Event_returnCorrectId_IfSetAfterConstruction() {
    Event event = new Event();
    int expectedId = 0;
    event.setId(expectedId);
    int actualId = event.getId();
    assertEquals(expectedId, actualId);
  }

  @Test
  public void Event_returnCorrectName_IfSetAfterConstruction() {
    Event event = new Event();
    String expectedName = "testName";
    event.setName(expectedName);
    String actualName = event.getName();
    assertEquals(expectedName, actualName);
  }

  @Test
  public void Event_returnCorrectLocation_IfSetAfterConstruction() {
    Event event = new Event();
    String expectedLocation = "testLocation";
    event.setLocation(expectedLocation);
    String actualLocation = event.getLocation();
    assertEquals(expectedLocation, actualLocation);
  }

  @Test
  public void Event_returnCorrectDescription_IfSetAfterConstruction() {
    Event event = new Event();
    String expectedDescription = "testDescription";
    event.setDescription(expectedDescription);
    String actualDescription = event.getDescription();
    assertEquals(expectedDescription, actualDescription);
  }

  @Test
  public void Event_returnCorrectDate_IfSetAfterConstruction() {
    Event event = new Event();
    Date expectedDate = new Date();
    event.setDate(expectedDate);
    Date actualDate = event.getDate();
    assertEquals(expectedDate, actualDate);
  }
}
