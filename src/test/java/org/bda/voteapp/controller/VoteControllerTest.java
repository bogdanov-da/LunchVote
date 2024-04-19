package org.bda.voteapp.controller;

import org.bda.voteapp.controller.user.AdminUserController;
import org.bda.voteapp.to.VoteTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.*;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.VoteController.REST_URL;
import static org.bda.voteapp.util.DateTimeUtil.setLocalTime;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteController controller;

    @BeforeEach
    void setUp() {
        newVote = new VoteTo(null, 100004, 100000, LocalDate.now());
    }

    @Test
    void getByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-user?id=" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(vote1, vote2));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + vote1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(vote1));
    }

    @Test
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?userId=" + user.getId() +
                "&restaurantId=" + restaurant2.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);

        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(controller.get(created.getId()), newVote);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + vote1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(AdminUserController.REST_URL + "/" + vote1.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void updateBefore11() throws Exception {
        int userId = user.getId();
        int restaurantId = restaurant2.getId();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?userId=" + userId + "&restaurantId=" + restaurantId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        restaurantId = restaurant3.getId();

        perform(MockMvcRequestBuilders.put(REST_URL + "?userId=" + userId + "&restaurantId=" + restaurantId
                        + "&localTime=" + setLocalTime("10:59:59"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        int newId = created.getId();
        newVote.setId(newId);
        newVote.setRestaurantId(restaurantId);

        VOTE_TO_MATCHER.assertMatch(controller.get(created.getId()), newVote);
    }

    @Test
    void updateAfter11() throws Exception {
        int userId = user.getId();
        int restaurantId = restaurant2.getId();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "?userId=" + userId + "&restaurantId=" + restaurantId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);

        perform(MockMvcRequestBuilders.put(REST_URL + "?userId=" + userId + "&restaurantId=" + restaurantId
                        + "&localTime=" + setLocalTime("11:00:01"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());

        int newId = created.getId();
        newVote.setId(newId);

        VOTE_TO_MATCHER.assertMatch(controller.get(created.getId()), newVote);
    }
}
