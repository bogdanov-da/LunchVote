package org.bda.voteapp.controller;

import org.bda.voteapp.model.Restaurant;
import org.bda.voteapp.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.RestaurantController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest extends AbstractControllerTest {
    @Autowired
    private RestaurantController restaurantController;

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1, restaurant2, restaurant3, restaurant4));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + restaurant3.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant3));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void getRestaurantOfDay() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/of-day"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2));
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.getId();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantController.get(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void update() throws Exception {
        int id = restaurant2.getId();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + id + "?name=" + newRestaurant.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        newRestaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(restaurantController.get(id), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_DETAILS)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + restaurant1.getId()))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantController.getAll(), restaurant2, restaurant3, restaurant4);
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void createForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
