package com.bringste.app.web.rest.dto;

import java.util.List;

public class ShoppingListsDto {
  List<ShoppingListDto> lists;

  public List<ShoppingListDto> getLists() {
    return lists;
  }

  public ShoppingListsDto() {
  }

  public ShoppingListsDto(List<ShoppingListDto> lists) {
    this.lists = lists;
  }
}
