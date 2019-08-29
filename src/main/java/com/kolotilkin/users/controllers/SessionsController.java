package com.kolotilkin.users.controllers;

import com.kolotilkin.users.models.User;
import com.kolotilkin.users.repositories.UsersRepository;
import com.kolotilkin.users.utils.JwtContent;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.keys.HmacKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionsController {

    @Autowired
    private UsersRepository repository;

    @PostMapping(path = "/session")
    public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password) {

        User user = repository.findByEmail(email);
        if(user == null) {
            return "{\"error\":\"User not found\"}";
        } else if (user.getPassword().equals(password)) {
            JwtContent content = new JwtContent(user.getId(), user.getEmail());

            JsonWebSignature jwt = new JsonWebSignature();
            jwt.setHeader("typ", "JWT");
            jwt.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
            jwt.setPayload(content.toJson());
            jwt.setKey(new HmacKey("5ab35573-ea1b-4b3b-9c19-43f52581a73f".getBytes()));

            try {
                String s = jwt.getCompactSerialization();
                return "{\"error\":\"SRV.JWS." + s + "\"}";
            } catch (Exception ex) {
                return "{\"error\":\"" + ex.getMessage() +"\"}";
            }
        } else {
            return "{\"error\":\"Incorrect password\"}";
        }
    }
}
