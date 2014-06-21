package com.bringste.app.web.rest;

import com.bringste.app.Application;
import com.bringste.app.domain.*;
import com.bringste.app.repository.ShoppingListRepository;
import com.bringste.app.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class ShoppingListResourceTest {

  @Inject
  private ShoppingListRepository shoppingListRepository;

  @Inject
  private UserRepository userRepository;

  private MockMvc restUserMockMvc;

  @Before
  public void setup() {
    ShoppingListResource shoppingListResource = new ShoppingListResource();
    ReflectionTestUtils.setField(shoppingListResource, "shoppingListRepository", shoppingListRepository);

    User user = userRepository.findOne("user");

    ShoppingList list = new ShoppingList().withId();

    list.setAssignee(user);
    list.setCreator(user);
    list.setTipType(TipType.CUSTOM);
    list.setTipDescription("1 Beer");
    list.setDueDate(new Date().getTime());
    list.setSourceLocation(new Location().withId().withName("source").withLatitude(new BigDecimal(1.0f)).withLongitude(new BigDecimal(1.0f)));
    list.setTargetLocation(new Location().withId().withName("target").withLatitude(new BigDecimal(1.0f)).withLongitude(new BigDecimal(1.0f)));

    List<Item> items = new ArrayList<>();
    items.add(new Item("Bread", false).withId());
    list.setItems(items);

    shoppingListRepository.save(list);

    this.restUserMockMvc = MockMvcBuilders.standaloneSetup(shoppingListResource).build();
  }

  @Test
  public void testGetShoppingLists() throws Exception {
    restUserMockMvc.perform(get("/app/rest/shopping-lists")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.lists").isArray())
      .andExpect(jsonPath("$.lists[0]").exists())
      .andExpect(jsonPath("$.lists[0].creatorId").value("omainge"))
      .andExpect(jsonPath("$.lists[0].assigneeId").value("user"))
      .andExpect(jsonPath("$.lists[1].assigneeId").doesNotExist())
      .andExpect(jsonPath("$.lists[0].tipType").value("CUSTOM"))
      .andExpect(jsonPath("$.lists[0].tipDescription").value("1 Beer"))
      .andExpect(jsonPath("$.lists[0].items").isArray())
      .andExpect(jsonPath("$.lists[0].items[0].name").value("Bread"))
      .andExpect(jsonPath("$.lists[0].items[0].done").value(false));
  }
}
