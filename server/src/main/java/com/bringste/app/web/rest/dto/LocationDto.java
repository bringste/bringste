package com.bringste.app.web.rest.dto;

import java.math.BigDecimal;

public class LocationDto {
  BigDecimal latitude;
  BigDecimal longitude;
  Integer zoom;
  String name;

  public BigDecimal getLatitude() {
    return latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public Integer getZoom() {
    return zoom;
  }

  public String getName() {
    return name;
  }

  public LocationDto withLatitude(BigDecimal latitude) {
    this.latitude = latitude;
    return this;
  }

  public LocationDto withLongitude(BigDecimal longitude) {
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
