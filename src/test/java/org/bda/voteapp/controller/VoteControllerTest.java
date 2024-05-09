package org.bda.voteapp.controller;

import org.bda.voteapp.to.VoteTo;
import org.bda.voteapp.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.VoteController.REST_URL;
import static org.bda.voteapp.util.DateTimeUtil.setDefaultTime;
import static org.bda.voteapp.util.DateTimeUtil.setFixedTime;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteController controller;

    @BeforeEach
    void setUp() {
        vote3 = new VoteTo(4, 2, 4, LocalDate.now());
    }

    @Test
    @WithUserDetails(value = USER2_DETAILS)
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(vote3));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/all-votes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(vote1, vote2));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + vote1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(vote1));
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant2)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.getId();
        newVote.setId(newId);

        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(controller.get(created.getId()), newVote);
    }

    @Test
    @WithUserDetails(value = USER2_DETAILS)
    void updateBefore11() throws Exception {
        setFixedTime("10:59:59");

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant3)))
                .andExpect(status().isNoContent());

        vote3.setRestaurantId(restaurant3.getId());
        VOTE_TO_MATCHER.assertMatch(controller.get(vote3.getId()), vote3);

        setDefaultTime();
    }

    @Test
    @WithUserDetails(value = USER2_DETAILS)
    void updateAfter11() throws Exception {
        setFixedTime("11:00:01");

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant4)))
                .andExpect(status().isUnprocessableEntity());

        setDefaultTime();
    }

    @Test
    @WithUserDetails(value = USER2_DETAILS)
    void createDuplicate() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}
