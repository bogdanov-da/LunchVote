package org.bda.voteapp.controller;

import org.bda.voteapp.controller.menu.AdminMenuController;
import org.bda.voteapp.controller.restaurant.UserRestaurantController;
import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.to.MenuTo;
import org.bda.voteapp.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.restaurant.AdminRestaurantController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuControllerTest extends AbstractControllerTest {
    @Autowired
    private AdminMenuController adminMenuController;
    @Autowired
    private DishController dishController;

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/" + restaurant4.getId() + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MenuTo created = MENU_TO_MATCHER.readFromJson(action);

        int newId = created.getId();
        newMenu.setId(newId);
        MENU_TO_MATCHER.assertMatch(created, newMenu);
        MENU_TO_MATCHER.assertMatch(adminMenuController.get(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void addDishes() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL +
                        "/" + restaurant4.getId() + "/menus/" + menu1.getId() + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(List.of(dish1, dish2))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        MenuTo created = MENU_TO_MATCHER.readFromJson(action);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurant4.getId());
        dish1.setId(created.getDishes().getFirst().getId());
        dish2.setId(created.getDishes().getLast().getId());
        dish1.setMenuId(menu1.getId());
        dish2.setMenuId(menu1.getId());
        menu1.setRestaurant(restaurant);
        menu1.setDishes(mapper.toDishes(List.of(dish1, dish2)));
        MENU_MATCHER.assertMatch(mapper.toModel(created), menu1);
        DISH_TO_MATCHER.assertMatch(dishController.getAll(created.getId()), dish1, dish2);
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void getAll() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurant4.getId());
        menu1.setRestaurant(restaurant);
        menu2.setRestaurant(restaurant);
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + restaurant4.getId() + "/menus"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(mapper.toTo(menu1), mapper.toTo(menu2)));
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void get() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurant4.getId());
        menu2.setRestaurant(restaurant);
        perform(MockMvcRequestBuilders.get(REST_URL + "/menus/" + menu2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(mapper.toTo(menu2)));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(UserRestaurantController.REST_URL + "/menus/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(List.of(menuTo)));
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void delete() throws Exception {
        int id = restaurant1.getId();
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + id + "/menus"))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_TO_MATCHER.assertMatch(adminMenuController.getAll(id), List.of());
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void createForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + restaurant4.getId() + "/menus")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
