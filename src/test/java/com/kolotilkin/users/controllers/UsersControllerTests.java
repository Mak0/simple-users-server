package com.kolotilkin.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolotilkin.users.models.User;
import com.kolotilkin.users.repositories.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTests {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    private User user;

    @Autowired
    private UsersRepository repo;

    @Before
    public void setup() {
        user = new User("1", "tester",  "test@test.com", "qwerty");
    }

    @After
    public void cleanup() {
        repo.deleteAll();
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        User user = new User("1", "tester",  "test@test.com", "qwerty");

        mvc.perform(post("/register")
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password", is(user.getPassword())));
    }

    @Test
    public void testRegisterExistingUser() throws Exception {
        repo.save(user);

        mvc.perform(post("/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeleteExistingUser() throws Exception {
        mvc.perform(delete("/users/" + repo.save(user).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNonExistingUser() throws Exception {
        mvc.perform(delete("/users/123" ))
                .andExpect(status().isNotFound());
    }
}
