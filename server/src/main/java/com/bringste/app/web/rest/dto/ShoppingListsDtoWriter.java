package com.bringste.app.web.rest.dto;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

public class ShoppingListsDtoWriter extends DefaultRepresentationWriter<ShoppingListsDto> {
  @Override
  public Representation write(ShoppingListsDto shoppingListsDto, RepresentationFactory factory) {
    return factory.newRepresentation().withBean(shoppingListsDto);
  }
}
