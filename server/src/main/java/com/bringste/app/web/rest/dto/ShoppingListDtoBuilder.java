package com.bringste.app.web.rest.dto;

import com.bringste.app.domain.TipType;

import java.util.Date;
import java.util.List;

public class ShoppingListDtoBuilder {
  private String id;
  private Date dueDate;
  private String tipDescription;
  private TipType tipType;
  private String creatorId;
  private String assigneeId;
  private LocationDto sourceLocation;
  private LocationDto targetLocation;
  private Boolean reserved;
  private List<ItemDto> items;

  public ShoppingListDtoBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ShoppingListDtoBuilder setDueDate(Date dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  public ShoppingListDtoBuilder setTipDescription(String tipDescription) {
    this.tipDescription = tipDescription;
    return this;
  }

  public ShoppingListDtoBuilder setTipType(TipType tipType) {
    this.tipType = tipType;
    return this;
  }

  public ShoppingListDtoBuilder setCreatorId(String creatorId) {
    this.creatorId = creatorId;
    return this;
  }

  public ShoppingListDtoBuilder setAssigneeId(String assigneeId) {
    this.assigneeId = assigneeId;
    return this;
  }

  public ShoppingListDtoBuilder setSourceLocation(LocationDto sourceLocation) {
    this.sourceLocation = sourceLocation;
    return this;
  }

  public ShoppingListDtoBuilder setTargetLocation(LocationDto targetLocation) {
    this.targetLocation = targetLocation;
    return this;
  }

  public ShoppingListDtoBuilder setReserved(Boolean reserved) {
    this.reserved = reserved;
    return this;
  }

  public ShoppingListDtoBuilder setItems(List<ItemDto> items) {
    this.items = items;
    return this;
  }

  public ShoppingListDto build() {
    return new ShoppingListDto(id, dueDate, tipDescription, tipType, creatorId, assigneeId, sourceLocation, targetLocation, reserved, items);
  }
}