package com.bringste.app.web.rest.dto;

public class LocationDto {
  float latitude;
  float longitude;
  Integer zoom;

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public Integer getZoom() {
    return zoom;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }

  public void setZoom(Integer zoom) {
    this.zoom = zoom;
  }

  public LocationDto(float latitude, float longitude, Integer zoom) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.zoom = zoom;
  }

  public LocationDto() {
  }
}
