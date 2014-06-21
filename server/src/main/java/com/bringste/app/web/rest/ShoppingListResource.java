package com.bringste.app.web.rest;

import com.bringste.app.domain.Item;
import com.bringste.app.domain.ShoppingList;
import com.bringste.app.repository.ShoppingListRepository;
import com.bringste.app.web.rest.dto.*;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/app")
@Transactional
public class ShoppingListResource {

  @Inject
  ShoppingListRepository shoppingListRepository;

  @RequestMapping(value = "/rest/shopping-lists",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ResponseEntity<ShoppingListsDto> getShoppingLists() {
    return new ResponseEntity<>(createShoppingListsDto(shoppingListRepository.findAll()), HttpStatus.OK);
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

      shoppingListDtoBuilder.setCreatorId(shoppingList.getCreator().getLogin());
      if (shoppingList.getAssignee() != null) {
        shoppingListDtoBuilder.setAssigneeId(shoppingList.getAssignee().getLogin());
      }
      shoppingListDtoBuilder.setTipType(shoppingList.getTipType())
        .setTipDescription(shoppingList.getTipDescription());
      shoppingListDtoBuilder.setItems(items);

      shoppingListDtoBuilder.setDueDate(new Date(shoppingList.getDueDate()))
        .setReserved(shoppingList.isReserved());

      shoppingListDtoBuilder.setTipAmount(shoppingList.getTipAmount());

      shoppingListDtos.add(shoppingListDtoBuilder.build());
    }

    return new ShoppingListsDto(shoppingListDtos);
  }

  @RequestMapping(value = "/rest/shopping-list/{id}/reserve", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> reserveShoppingList(@PathVariable("id") String id) {
    ShoppingList shoppingList = shoppingListRepository.findOne(id);
    if (shoppingList.isReserved()) {
      return new ResponseEntity<>("conflict, list is already reserved", HttpStatus.CONFLICT);
    } else {
      ShoppingList list = shoppingListRepository.findOne(id);
      list.setReserved(true);
      shoppingListRepository.save(list);
      return new ResponseEntity<>("reserved", HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/rest/shopping-list/{id}/unreserve", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> ureserveShoppingList(@PathVariable("id") String id) {
    ShoppingList shoppingList = shoppingListRepository.findOne(id);
    if (shoppingList.isReserved()) {
      ShoppingList list = shoppingListRepository.findOne(id);
      list.setReserved(false);
      shoppingListRepository.save(list);
      return new ResponseEntity<>("unreserved", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("conflict, list is already unreserved", HttpStatus.CONFLICT);
    }
  }

}
