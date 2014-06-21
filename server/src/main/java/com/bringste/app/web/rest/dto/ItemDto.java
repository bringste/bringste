package com.bringste.app.web.rest.dto;

public class ItemDto {
  String id;
  String name;
  boolean done;

  public ItemDto(String id,String name, boolean done) {
    this.name = name;
    this.done = done;
    this.id = id;
  }

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
}
