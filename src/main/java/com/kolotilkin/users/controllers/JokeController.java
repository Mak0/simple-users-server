package com.kolotilkin.users.controllers;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class JokeController {

    @GetMapping("/joke")
    public ResponseEntity<String> joke() {
        RestTemplate template = new RestTemplate();
        String response = template.getForObject("http://api.icndb.com/jokes/random", String.class);

        String joke = new JSONObject(response).getJSONObject("value").getString("joke");
        return new ResponseEntity<>(joke, HttpStatus.I_AM_A_TEAPOT);
    }
}
