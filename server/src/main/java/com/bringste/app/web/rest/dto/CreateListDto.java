package com.bringste.app.web.rest.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreateListDto {
  boolean deliverHome;
  Date dueDate = new Date();
  List<ItemDto> items;
  BigDecimal tipAmount;

  public boolean isDeliverHome() {
    return deliverHome;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public List<ItemDto> getItems() {
    return items;
  }

  public BigDecimal getTipAmount() {
    return tipAmount;
  }

  public void setDeliverHome(boolean deliverHome) {
    this.deliverHome = deliverHome;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public void setItems(List<ItemDto> items) {
    this.items = items;
  }

  public void setTipAmount(BigDecimal tipAmount) {
    this.tipAmount = tipAmount;
  }
}
