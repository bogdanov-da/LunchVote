package org.bda.voteapp.controller;

import org.bda.voteapp.model.User;
import org.bda.voteapp.repository.UserRepository;
import org.bda.voteapp.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.user.ProfileUserController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileUserControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void register() throws Exception {
        User newUser = mapper.toModel(newUserTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.id();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.getById(newId), newUser);
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)).andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.findAll(), admin, guest, phil);
    }

    @Test
    @WithUserDetails(value = USER_DETAILS)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo)))
                .andDo(print())
                .andExpect(status().isNoContent());
        newUser.setId(user.getId());
        USER_MATCHER.assertMatch(repository.getById(user.getId()), newUser);
    }
}
