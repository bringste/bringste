package com.bringste.app.web.rest.dto;

import com.bringste.app.domain.TipType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingListDto {
  TipType tipType;
  String id;
  Date dueDate;
  String tipDescription;
  BigDecimal tipAmount;
  ListUserDto creator;
  ListUserDto assignee;
  LocationDto sourceLocation;
  LocationDto targetLocation;
  Boolean reserved;

  List<ItemDto> items = new ArrayList<>();

  public String getId() {
    return id;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public String getTipDescription() {
    return tipDescription;
  }

  public BigDecimal getTipAmount() {
    return tipAmount;
  }

  public LocationDto getSourceLocation() {
    return sourceLocation;
  }

  public LocationDto getTargetLocation() {
    return targetLocation;
  }

  public Boolean getReserved() {
    return reserved;
  }

  public List<ItemDto> getItems() {
    return items;
  }

  public TipType getTipType() {
    return tipType;
  }

  public ListUserDto getCreator() {
    return creator;
  }

  public ListUserDto getAssignee() {
    return assignee;
  }

  public ShoppingListDto setTipType(TipType tipType) {
    this.tipType = tipType;
    return this;
  }

  public ShoppingListDto setId(String id) {
    this.id = id;
    return this;
  }

  public ShoppingListDto setDueDate(Date dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  public ShoppingListDto setTipDescription(String tipDescription) {
    this.tipDescription = tipDescription;
    return this;
  }

  public ShoppingListDto setTipAmount(BigDecimal tipAmount) {
    this.tipAmount = tipAmount;
    return this;
  }

  public ShoppingListDto setCreator(ListUserDto creator) {
    this.creator = creator;
    return this;
  }

  public ShoppingListDto setAssignee(ListUserDto assignee) {
    this.assignee = assignee;
    return this;
  }

  public ShoppingListDto setSourceLocation(LocationDto sourceLocation) {
    this.sourceLocation = sourceLocation;
    return this;
  }

  public ShoppingListDto setTargetLocation(LocationDto targetLocation) {
    this.targetLocation = targetLocation;
    return this;
  }

  public ShoppingListDto setReserved(Boolean reserved) {
    this.reserved = reserved;
    return this;
  }

  public ShoppingListDto setItems(List<ItemDto> items) {
    this.items = items;
    return this;
  }

  public ShoppingListDto() {
  }
}
