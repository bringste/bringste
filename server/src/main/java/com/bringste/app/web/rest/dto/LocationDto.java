package com.bringste.app.web.rest.dto;

public class LocationDto {
  float latitude;
  float longitude;
  Integer zoom;
  String name;

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public Integer getZoom() {
    return zoom;
  }

  public String getName() {
    return name;
  }

  public LocationDto withLatitude(float latitude) {
    this.latitude = latitude;
    return this;
  }

  public LocationDto withLongitude(float longitude) {
    this.longitude = longitude;
    return this;
  }

  public LocationDto withZoom(Integer zoom) {
    this.zoom = zoom;
    return this;
  }

  public LocationDto withName(String name) {
    this.name = name;
    return this;
  }

  public LocationDto() {
  }
}
