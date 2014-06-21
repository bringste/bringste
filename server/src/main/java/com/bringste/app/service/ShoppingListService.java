package com.bringste.app.service;

import com.bringste.app.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service
@Transactional
public class ShoppingListService {
  @Inject
  private ShoppingListRepository shoppingListRepository;

}
