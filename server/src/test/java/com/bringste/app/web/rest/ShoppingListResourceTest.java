package com.bringste.app.web.rest;

import com.bringste.app.Application;
import com.bringste.app.domain.*;
import com.bringste.app.repository.ShoppingListRepository;
import com.bringste.app.repository.UserRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    list.setTipAmount(new BigDecimal(1.0));
    list.setTipDescription("Beer");
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
      .accept("application/json"))
      .andExpect(status().isOk())
      .andExpect(content().contentType("application/json"))
      .andExpect(jsonPath("$.lists").isArray())
      .andExpect(jsonPath("$.lists[0]").exists())
      .andExpect(jsonPath("$.lists[0].creator.login").value("omainge"))
      .andExpect(jsonPath("$.lists[0].creator.name").value("Oma Inge"))
      .andExpect(jsonPath("$.lists[0].creator.avatarUrl").value("http://lorempixel.com/42/42/people/omainge"))
      .andExpect(jsonPath("$.lists[0].assignee.login").value("user"))
      .andExpect(jsonPath("$.lists[0].sourceLocation.name").value("Rossmann am Umspannwerk"))
      .andExpect(jsonPath("$.lists[0].tipType").value("CUSTOM"))
      .andExpect(jsonPath("$.lists[0].tipDescription").value("Beer"))
      .andExpect(jsonPath("$.lists[0].tipAmount").value(1.0))
      .andExpect(jsonPath("$.lists[0].items").isArray())
      .andExpect(jsonPath("$.lists[0].items[0].id").value("item-1"))
      .andExpect(jsonPath("$.lists[0].items[0].name").value("Bread"))
      .andExpect(jsonPath("$.lists[0].items[0].done").value(false))
      .andExpect(jsonPath("$.lists[1].items").isArray())
      .andExpect(jsonPath("$.lists[1].assignee").doesNotExist());
  }

  @Test
  @Ignore("currently broken because the auth. is not mocked yet")
  public void testListReservation() throws Exception {
    MockHttpServletRequestBuilder reservePost = post("/app/rest/shopping-list/list-1/reserve").with(new RequestPostProcessor() {
      public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.setRemoteUser("user");
        return request;
      }
    });

    MockHttpServletRequestBuilder unreservePost = post("/app/rest/shopping-list/list-1/unreserve").with(new RequestPostProcessor() {
      public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
        request.setRemoteUser("user");
        return request;
      }
    });

    restUserMockMvc.perform(reservePost)
      .andExpect(status().isConflict());

    restUserMockMvc.perform(unreservePost)
      .andExpect(status().isOk());

    restUserMockMvc.perform(unreservePost)
      .andExpect(status().isConflict());

    restUserMockMvc.perform(reservePost)
      .andExpect(status().isOk());
  }
}
