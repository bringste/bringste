package com.bringste.app.repository;

import com.bringste.app.domain.ShoppingList;
import com.bringste.app.domain.User;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, String> {
  @Query("select s from ShoppingList s where s.creator.login = ?1")
  List<ShoppingList> findShoppingListsByCreator(String login);

  @Query("select s from ShoppingList s where s.assignee.login = ?1")
  List<ShoppingList> findShoppingListsByAssignee(String login);
}
