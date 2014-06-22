package com.bringste.app.web.rest;

import com.bringste.app.domain.*;
import com.bringste.app.repository.ShoppingListRepository;
import com.bringste.app.repository.UserRepository;
import com.bringste.app.web.rest.dto.*;
import com.codahale.metrics.annotation.Timed;
import com.paypal.api.payments.Payment;
import com.paypal.core.rest.APIContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/app")
@Transactional
public class ShoppingListResource {

  @Inject
  ShoppingListRepository shoppingListRepository;

  @Inject
  UserRepository userRepository;

  @Inject
  APIContext apiContext;

  @RequestMapping(value = "/rest/shopping-lists",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ResponseEntity<ShoppingListsDto> getShoppingLists() {
    return new ResponseEntity<>(createShoppingListsDto(shoppingListRepository.findAll()), HttpStatus.OK);
  }

  private ShoppingListsDto createShoppingListsDto(List<ShoppingList> shoppingLists) {
    List<ShoppingListDto> shoppingListDtos = new ArrayList<>();

    for (ShoppingList shoppingList: shoppingLists) {
      ShoppingListDto shoppingListDtoBuilder = createShoppingListDto(shoppingList);
      shoppingListDtos.add(shoppingListDtoBuilder);
    }

    return new ShoppingListsDto(shoppingListDtos);
  }

  private ShoppingListDto createShoppingListDto(ShoppingList shoppingList) {
    List<ItemDto> items = new ArrayList<>();
    for (Item item : shoppingList.getItems()) {
      items.add(new ItemDto().withDone(item.getDone()).withName(item.getName()).withId(item.getId()));
    }

    ShoppingListDto shoppingListDtoBuilder = new ShoppingListDto();
    shoppingListDtoBuilder.setId(shoppingList.getId());

    if (shoppingList.getSourceLocation() != null) {
      Integer sourceZoom = shoppingList.getSourceLocation().getZoom();
      BigDecimal sourceLongitude = shoppingList.getSourceLocation().getLongitude();
      BigDecimal sourceLatitude = shoppingList.getSourceLocation().getLatitude();

      LocationDto sourceLocation = new LocationDto()
        .withLatitude(sourceLatitude)
        .withLongitude(sourceLongitude)
        .withZoom(sourceZoom)
        .withName(shoppingList.getSourceLocation().getName());

      shoppingListDtoBuilder.setSourceLocation(sourceLocation);
    }

    shoppingListDtoBuilder.setTargetLocation(new LocationDto()
      .withLatitude(shoppingList.getTargetLocation().getLatitude())
      .withLongitude(shoppingList.getTargetLocation().getLongitude())
      .withZoom(shoppingList.getTargetLocation().getZoom())
      .withName(shoppingList.getTargetLocation().getName()));

    shoppingListDtoBuilder.setCreator(listUserDtoFromUser(shoppingList.getCreator()));

    if (shoppingList.getAssignee() != null) {
      shoppingListDtoBuilder.setAssignee(listUserDtoFromUser(shoppingList.getAssignee()));
    }
    shoppingListDtoBuilder.setTipType(shoppingList.getTipType())
      .setTipDescription(shoppingList.getTipDescription());
    shoppingListDtoBuilder.setItems(items);

    if (shoppingList.getDueDate() != null) {
      shoppingListDtoBuilder.setDueDate(new Date(shoppingList.getDueDate()));
    }

    shoppingListDtoBuilder.setReserved(shoppingList.isReserved());
    shoppingListDtoBuilder.setTipAmount(shoppingList.getTipAmount());
    return shoppingListDtoBuilder;
  }

  private ListUserDto listUserDtoFromUser(User user) {
    return new ListUserDto()
      .withLogin(user.getLogin())
      .withName(String.format("%s %s", user.getFirstName(), user.getLastName()))
      .withAvatarUrl("http://lorempixel.com/42/42/people/" + user.getLogin());
  }

  @RequestMapping(value = "/rest/shopping-list/{id}",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ResponseEntity<ShoppingListDto> getShoppingList(@PathVariable("id") String id) {
    return new ResponseEntity<>(createShoppingListDto(shoppingListRepository.findOne(id)), HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/shopping-list/{id}/reserve", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> reserveShoppingList(@PathVariable("id") String id, Principal principal) {
    ShoppingList shoppingList = shoppingListRepository.findOne(id);
    if (shoppingList.isReserved()) {
      return new ResponseEntity<>("conflict, list is already reserved", HttpStatus.CONFLICT);
    } else {
      ShoppingList list = shoppingListRepository.findOne(id);
      list.setReserved(true);
      list.setAssignee(userRepository.findOne(principal.getName()));
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
      list.setAssignee(null);
      shoppingListRepository.save(list);
      return new ResponseEntity<>("unreserved", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("conflict, list is already unreserved", HttpStatus.CONFLICT);
    }
  }

  @RequestMapping(value = "/rest/shopping-list/{id}/transfer-tip", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> transferTip(@PathVariable("id") String id) {
    Payment payment = new Payment();
    payment.setIntent("sale");
    return new ResponseEntity<>("transfered", HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/shopping-list/new", method = RequestMethod.POST)
  @Timed
  public ResponseEntity<String> createNewList(@RequestBody CreateListDto createListDto, Principal principal) {
    ShoppingList newList = new ShoppingList().withId();
    newList.setCreator(userRepository.findOne(principal.getName()));

    newList.setTipAmount(createListDto.getTipAmount());
    newList.setTipType(TipType.PAYPAL);

    if (createListDto.getDueDate() != null) {
      newList.setDueDate(createListDto.getDueDate().getTime());
    }

    if (createListDto.isDeliverHome()) {
      newList.setTargetLocation(userRepository.findOne(principal.getName()).getHomeLocation());
    } else if (createListDto.getTargetLocation() == null) {
      return new ResponseEntity<>("either home or target location must be set", HttpStatus.CONFLICT);
    } else {
      newList.setTargetLocation(new Location()
        .withId()
        .withLatitude(createListDto.getTargetLocation().getLatitude())
        .withLongitude(createListDto.getTargetLocation().getLongitude())
      );
    }

    for (ItemDto itemDto : createListDto.getItems()) {
      Item item = new Item().withId();
      if (itemDto.getName() == null || itemDto.getName().isEmpty()) {
        continue;
      }
      item.setName(itemDto.getName());
      item.setDone(false);

      List<Item> items = new ArrayList<>();
      items.add(item);
      newList.setItems(items);
    }

    shoppingListRepository.save(newList);
    return new ResponseEntity<>("created", HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/shopping-lists/created",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ResponseEntity<ShoppingListsDto> getUserShoppingLists(Principal principal) {
    return new ResponseEntity<>(createShoppingListsDto(shoppingListRepository.findShoppingListsByCreator(principal.getName())), HttpStatus.OK);
  }

  @RequestMapping(value = "/rest/shopping-lists/assigned",
    method = RequestMethod.GET, produces = {"application/json"})
  @Timed
  public ResponseEntity<ShoppingListsDto> getShoppingListsAssignedToMe(Principal principal) {
    return new ResponseEntity<>(createShoppingListsDto(shoppingListRepository.findShoppingListsByAssignee(principal.getName())), HttpStatus.OK);
  }
}
