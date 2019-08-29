package com.kolotilkin.users.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JokeControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    JokeController controller;

    @Test
    public void testRandomJoke() throws Exception {
        when(controller.joke()).thenReturn(new ResponseEntity<>("Chuck Norris is always watching",
                HttpStatus.I_AM_A_TEAPOT));

        mvc.perform(MockMvcRequestBuilders.get("/joke").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isIAmATeapot())
                .andExpect(content().string(equalTo("Chuck Norris is always watching")));
    }
}
