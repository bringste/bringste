package com.bringste.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name = "T_ITEM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item {

  @NotNull
  @Size(min = 0, max = 50)
  @Id
  private String id;

  @NotNull
  @Size(min = 0, max = 255)
  String name;

  @NotNull
  Boolean done;

  public Item(String name, Boolean done) {
    this.name = name;
    this.done = done;
  }

  public Item() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDone() {
    return done;
  }

  public void setDone(Boolean done) {
    this.done = done;
  }

  public Item withId() {
    this.id = UUID.randomUUID().toString();
    return this;
  }
}
