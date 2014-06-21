package com.bringste.app.web.rest.dto;

public class LocationDtoBuilder {
  private float latitude;
  private float longitude;
  private Integer zoom;

  public LocationDtoBuilder setLatitude(float latitude) {
    this.latitude = latitude;
    return this;
  }

  public LocationDtoBuilder setLongitude(float longitude) {
    this.longitude = longitude;
    return this;
  }

  public LocationDtoBuilder setZoom(Integer zoom) {
    this.zoom = zoom;
    return this;
  }

  public LocationDto build() {
    return new LocationDto(latitude, longitude, zoom);
  }
}