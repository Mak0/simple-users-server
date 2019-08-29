package com.kolotilkin.users.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "{status:'up'}";
    }

    @GetMapping("/joke")
    public String joke() {
        RestTemplate template = new RestTemplate();
        return template.getForObject("http://api.icndb.com/jokes/random", String.class);
    }
}
