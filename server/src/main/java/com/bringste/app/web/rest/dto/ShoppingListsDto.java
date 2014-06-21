package com.bringste.app.web.rest.dto;

import net.eusashead.hateoas.hal.adapter.annotation.RepresentationWriterAdapter;

import java.util.List;

@RepresentationWriterAdapter(ShoppingListsDtoWriter.class)
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
