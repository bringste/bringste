package com.bringste.app.web.rest.dto;

public class ItemDto {
  String id;
  String name;
  boolean done;

  public ItemDto() {
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isDone() {
    return done;
  }

  public ItemDto withId(String id) {
    this.id = id;
    return this;
  }

  public ItemDto withName(String name) {
    this.name = name;
    return this;
  }

  public ItemDto withDone(boolean done) {
    this.done = done;
    return this;
  }
}
