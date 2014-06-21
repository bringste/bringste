package com.bringste.app.web.rest;

import com.bringste.app.domain.Item;
import com.bringste.app.domain.ShoppingList;
import com.bringste.app.repository.ShoppingListRepository;
import com.bringste.app.web.rest.dto.*;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/app")
public class ShoppingListResource {

  @Inject
  ShoppingListRepository shoppingListRepository;

  /**
   * GET  /rest/shopping-lists -> get the shopping lists .
   */
  @RequestMapping(value = "/rest/shopping-lists",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ShoppingListsDto getShoppingLists() {
    return createShoppingListsDto(shoppingListRepository.findAll());
  }

  private ShoppingListsDto createShoppingListsDto(List<ShoppingList> shoppingLists) {
    List<ShoppingListDto> shoppingListDtos = new ArrayList<>();

    for (ShoppingList shoppingList: shoppingLists) {
      List<ItemDto> items = new ArrayList<>();
      for (Item item : shoppingList.getItems()) {
        items.add(new ItemDtoBuilder().setDone(item.getDone()).setName(item.getName()).setId(item.getId()).build());
      }

      ShoppingListDtoBuilder shoppingListDtoBuilder = new ShoppingListDtoBuilder()
        .setId(shoppingList.getId());

      Integer sourceZoom = shoppingList.getSourceLocation().getZoom();
      float sourceLongitude = shoppingList.getSourceLocation().getLongitude().floatValue();
      float sourceLatitude = shoppingList.getSourceLocation().getLatitude().floatValue();

      LocationDto sourceLocation = new LocationDtoBuilder()
        .setLatitude(sourceLatitude)
        .setLongitude(sourceLongitude)
        .setZoom(sourceZoom)
        .build();

      shoppingListDtoBuilder.setSourceLocation(sourceLocation);

      shoppingListDtoBuilder.setTargetLocation(new LocationDtoBuilder()
        .setLatitude(shoppingList.getTargetLocation().getLatitude().floatValue())
        .setLongitude(shoppingList.getTargetLocation().getLongitude().floatValue())
        .setZoom(shoppingList.getTargetLocation().getZoom())
        .build());

      shoppingListDtoBuilder.setCreatorId(shoppingList.getCreator().getLogin())
        .setAssigneeId(shoppingList.getAssignee().getLogin())
        .setTipType(shoppingList.getTipType())
        .setTipDescription(shoppingList.getTipDescription());
      shoppingListDtoBuilder.setItems(items);

      shoppingListDtoBuilder.setDueDate(new Date(shoppingList.getDueDate()))
        .setReserved(shoppingList.isReserved());

      shoppingListDtos.add(shoppingListDtoBuilder.build());
    }

    return new ShoppingListsDto(shoppingListDtos);
  }

  @RequestMapping(value = "/rest/shopping-lists/{id}/reserve", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> reserveShoppingList(@PathVariable("id") String id) {
    return new ResponseEntity<String>("ok", HttpStatus.OK);
  }
}
