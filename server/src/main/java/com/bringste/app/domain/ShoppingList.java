package com.bringste.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * A Shopping list.
 */
@Entity
@Table(name = "T_SHOPPING_LIST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShoppingList implements Serializable {

  @NotNull
  @Size(min = 0, max = 50)
  @Id
  private String id;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  @Column(name = "tip_type")
  private TipType tipType;

  @Size(max = 255)
  @Column(name = "tip_description")
  private String tipDescription;

  @OneToOne(optional = false)
  @JoinColumn(name = "creator_id")
  private User creator;

  @OneToOne(optional = true)
  @JoinColumn(name = "assignee_id")
  private User assignee;

  @NotNull
  @Column(name = "due_date")
  private Long dueDate;

  @NotNull
  @Column(name = "reserved")
  boolean reserved;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "source_location_id")
  Location sourceLocation;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "target_location_id")
  Location targetLocation;

  @JsonIgnore
  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "shopping_list_id")
  @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
  private List<Item> items;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getAssignee() {
    return assignee;
  }

  public void setAssignee(User assignee) {
    this.assignee = assignee;
  }

  public Long getDueDate() {
    return dueDate;
  }

  public void setDueDate(Long dueDate) {
    this.dueDate = dueDate;
  }

  public boolean isReserved() {
    return reserved;
  }

  public void setReserved(boolean reserved) {
    this.reserved = reserved;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public Location getSourceLocation() {
    return sourceLocation;
  }

  public void setSourceLocation(Location sourceLocation) {
    this.sourceLocation = sourceLocation;
  }

  public Location getTargetLocation() {
    return targetLocation;
  }

  public void setTargetLocation(Location targetLocation) {
    this.targetLocation = targetLocation;
  }

  public TipType getTipType() {
    return tipType;
  }

  public void setTipType(TipType tipType) {
    this.tipType = tipType;
  }

  public String getTipDescription() {
    return tipDescription;
  }

  public void setTipDescription(String tipDescription) {
    this.tipDescription = tipDescription;
  }

  public ShoppingList withId() {
    this.id = UUID.randomUUID().toString();
    return this;
  }

  @Override
  public String toString() {
    return "ShoppingList{" +
      "id='" + id + '\'' +
      ", dueDate=" + dueDate +
      ", reserved=" + reserved +
      ", sourceLocation='" + sourceLocation + '\'' +
      ", targetLocation='" + targetLocation + '\'' +
      '}';
  }
}
