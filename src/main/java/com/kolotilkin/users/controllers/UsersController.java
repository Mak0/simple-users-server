package com.kolotilkin.users.controllers;

import com.kolotilkin.users.models.User;
import com.kolotilkin.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
public class UsersController {

    @Autowired
    UsersRepository repository;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        try {
            if (repository.findByEmail(user.getEmail()) != null)
                throw new RuntimeException("User already exists");

            return repository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("User was not created: " + ex.getMessage());
        }
    }

    @DeleteMapping(path = "/users/{id}")
    public String delete(@PathVariable("id") String id) {
        try {
            repository.deleteById(id);
            return "{\"success\":\"User was successfully deleted\"}";
        } catch (EmptyResultDataAccessException ex) {
            return "{\"error\":\"No such user\"}";
        }
    }

}
