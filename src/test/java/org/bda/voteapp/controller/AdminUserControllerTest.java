package org.bda.voteapp.controller;

import org.bda.voteapp.controller.user.AdminUserController;
import org.bda.voteapp.model.User;
import org.bda.voteapp.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.bda.voteapp.TestData.*;
import static org.bda.voteapp.controller.user.AdminUserController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = ADMIN_ROLE)
public class AdminUserControllerTest extends AbstractControllerTest {
    @Autowired
    private AdminUserController adminUserController;

    @Test
    void create() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(newUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User created = USER_MATCHER.readFromJson(action);
        int newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(adminUserController.get(newId), newUser);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + admin.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(admin));
    }

    @Test
    void getByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-email?email=" + user.getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user, admin, guest));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + "/" + user.getId()))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(adminUserController.getAll(null, null), admin, guest);
    }

    @Test
    void getNotFoundById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotFoundByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-email?email=notFound@email.ru"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails
    void getAllForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
