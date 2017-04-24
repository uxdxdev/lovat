package com.bitbosh.DropwizardHeroku.api;

import java.util.Date;

public class Event {
  private int id;
  private String name;
  private String description;
  private String location;
  private Date date;

  public Event() {
    id = 0;
    name = "";
    description = "";
    location = "";
    date = new Date();
  }

  public Event(String name, String location, String description, Date date) {
    this.name = name;
    this.location = location;
    this.description = description;
    this.date = date;
  }

  public Event(int id, String name, String location, String description, Date date) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.description = description;
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
