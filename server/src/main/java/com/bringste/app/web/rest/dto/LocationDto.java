package com.bringste.app.web.rest.dto;

public class LocationDto {
  float x;
  float y;
  Integer zoom;

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public Integer getZoom() {
    return zoom;
  }

  public void setX(float x) {
    this.x = x;
  }

  public void setY(float y) {
    this.y = y;
  }

  public void setZoom(Integer zoom) {
    this.zoom = zoom;
  }

  public LocationDto(float x, float y, Integer zoom) {
    this.x = x;
    this.y = y;
    this.zoom = zoom;
  }

  public LocationDto() {
  }
}
