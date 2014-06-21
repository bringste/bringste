package com.bringste.app.web.rest.dto;

import com.bringste.app.domain.TipType;
import net.eusashead.hateoas.hal.adapter.annotation.RepresentationWriterAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RepresentationWriterAdapter(ShoppingListDtoWriter.class)
public class ShoppingListDto {
  TipType tipType;
  String id;
  Date dueDate;
  String tipDescription;
  String creatorId;
  String assigneeId;
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

  public String getCreatorId() {
    return creatorId;
  }

  public String getAssigneeId() {
    return assigneeId;
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

  public ShoppingListDto(String id, Date dueDate, String tipDescription, TipType tipType, String creatorId, String assigneeId, LocationDto sourceLocation, LocationDto targetLocation, Boolean reserved, List<ItemDto> items) {
    this.id = id;
    this.dueDate = dueDate;
    this.tipDescription = tipDescription;
    this.tipType = tipType;
    this.creatorId = creatorId;
    this.assigneeId = assigneeId;
    this.sourceLocation = sourceLocation;
    this.targetLocation = targetLocation;
    this.reserved = reserved;
    this.items = items;
  }

  public ShoppingListDto() {
  }
}
