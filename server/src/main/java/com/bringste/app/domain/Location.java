package com.bringste.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "T_LOCATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Location {

  @NotNull
  @Size(min = 0, max = 50)
  @Id
  String id;

  @NotNull
  @Size(min = 0, max = 255)
  @Column(name = "name")
  String name;

  @NotNull
  @Column(name = "longitude")
  BigDecimal longitude;

  @NotNull
  @Column(name = "latitude")
  BigDecimal latitude;

  @Column(name = "zoom")
  Integer zoom;

  @OneToOne
  @JoinColumn(name = "shopping_list_id")
  ShoppingList shoppingList;

  public String getId() {
    return id;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public Location withLongitude(BigDecimal longitude) {
    this.longitude = longitude;
    return this;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public Location withLatitude(BigDecimal latitude) {
    this.latitude = latitude;
    return this;
  }

  public Integer getZoom() {
    return zoom;
  }

  public Location withZoom(Integer zoom) {
    this.zoom = zoom;
    return this;
  }

  public String getName() {
    return name;
  }

  public Location withName(String name) {
    this.name = name;
    return this;
  }

  public ShoppingList getShoppingList() {
    return shoppingList;
  }

  public Location withId() {
    this.id = UUID.randomUUID().toString();
    return this;
  }

  @Override
  public String toString() {
    return "Location{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", longitude=" + longitude +
      ", latitude=" + latitude +
      ", zoom=" + zoom +
      '}';
  }
}
