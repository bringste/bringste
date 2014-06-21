package com.bringste.app.web.rest.dto;

public class LocationDtoBuilder {
  private float x;
  private float y;
  private Integer zoom;

  public LocationDtoBuilder setX(float x) {
    this.x = x;
    return this;
  }

  public LocationDtoBuilder setY(float y) {
    this.y = y;
    return this;
  }

  public LocationDtoBuilder setZoom(Integer zoom) {
    this.zoom = zoom;
    return this;
  }

  public LocationDto build() {
    return new LocationDto(x, y, zoom);
  }
}