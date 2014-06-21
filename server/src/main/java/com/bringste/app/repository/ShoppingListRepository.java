package com.bringste.app.repository;

import com.bringste.app.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, String> {
}
