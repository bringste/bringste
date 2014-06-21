package com.bringste.app.web.rest;

import com.bringste.app.repository.ItemRepository;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/app")
public class ItemResource {

  @Autowired
  ItemRepository itemRepository;

  /**
   * POST  /rest/item/{id}/done -> set item done.
   */
  @RequestMapping(value = "/rest/item/{id}/done", method = RequestMethod.GET)
  @Timed
  public ResponseEntity<String> setDone(@PathVariable("id") String id) {
    itemRepository.findOne(id).setDone(true);
    return new ResponseEntity<>("done", HttpStatus.OK);
  }
}
