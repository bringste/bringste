package com.bringste.app.web.rest.dto;

public class ItemDtoBuilder {
  private String name;
  private boolean done;
  private String id;

  public ItemDtoBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ItemDtoBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public ItemDtoBuilder setDone(boolean done) {
    this.done = done;
    return this;
  }

  public ItemDto build() {
    return new ItemDto(id, name, done);
  }
}