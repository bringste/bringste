package com.bringste.app.web.rest.dto;

import com.bringste.app.web.rest.ShoppingListResource;
import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;

public class ShoppingListDtoWriter extends DefaultRepresentationWriter<ShoppingListDto> {
  @Override
  public Representation write(ShoppingListDto shoppingListDto, RepresentationFactory factory) {
    return factory.newRepresentation()
      .withBean(shoppingListDto)
      .withLink("reserve", methodUrl(ShoppingListResource.class, "reserveShoppingList", String.class));
  }
}
