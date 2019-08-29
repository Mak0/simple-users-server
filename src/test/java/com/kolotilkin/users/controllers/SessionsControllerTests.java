package com.kolotilkin.users.controllers;

import com.kolotilkin.users.models.User;
import com.kolotilkin.users.repositories.UsersRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SessionsControllerTests {

    @Autowired
    MockMvc mvc;

    private User user;

    @Autowired
    private UsersRepository repo;

    @Before
    public void setup() {
        user = new User("1", "tester",  "test@test.com", "qwerty");
        repo.save(user);
    }

    @After
    public void cleanup() {
        repo.deleteAll();
    }

    @Test
    public void testGetSession() throws Exception {
        mvc.perform(post("/session?email=test@test.com&password=qwerty"))
                .andExpect(status().isCreated());
    }


    @Test
    public void testGetSessionWithIncorrectPassword() throws Exception {
        mvc.perform(post("/session?email=test@test.com&password=123456"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetSessionWithNonExistingUser() throws Exception {
        mvc.perform(post("/session?email=test@test.ua&password=123456"))
                .andExpect(status().isNotFound());
    }

}
