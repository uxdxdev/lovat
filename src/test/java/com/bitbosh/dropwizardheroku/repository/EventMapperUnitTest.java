package com.bitbosh.DropwizardHeroku.repository;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.skife.jdbi.v2.StatementContext;

import com.bitbosh.DropwizardHeroku.api.Event;

import mockit.Expectations;
import mockit.Mocked;

public class EventMapperUnitTest {

  @Test
  public void map_returnsCorrectEvent_IfResultSetValid(@Mocked ResultSet resultSet,
      @Mocked StatementContext unusedStatementContext) throws SQLException {

    int expectedId = 3;
    String expectedName = "testName";
    String expectedLocation = "testLocation";
    String expectedDescription = "testDescription";
    Date expectedDate = null;

    new Expectations() {
      {
        resultSet.getInt("id");
        result = expectedId;

        resultSet.getString("name");
        result = expectedName;

        resultSet.getString("location");
        result = "testLocation";

        resultSet.getString("description");
        result = "testDescription";

        resultSet.getDate("date");
        result = expectedDate;
      }
    };

    EventMapper eventMapper = new EventMapper();
    int unusedIndex = 0;
    Event event = eventMapper.map(unusedIndex, resultSet, unusedStatementContext);

    int actualId = event.getId();
    String actualName = event.getName();
    String actualLocation = event.getLocation();
    String actualDescription = event.getDescription();
    Date actualDate = null;

    assertEquals(expectedId, actualId);
    assertEquals(expectedName, actualName);
    assertEquals(expectedLocation, actualLocation);
    assertEquals(expectedDescription, actualDescription);
    assertEquals(expectedDate, actualDate);
  }
}
