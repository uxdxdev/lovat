package com.bitbosh.DropwizardHeroku.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.bitbosh.DropwizardHeroku.api.Event;

public class EventMapper implements ResultSetMapper<Event> {

  public Event map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    return new Event(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("location"),
        resultSet.getString("description"), resultSet.getDate("date"));
  }
}
