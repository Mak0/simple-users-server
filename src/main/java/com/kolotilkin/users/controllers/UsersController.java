package com.kolotilkin.users.controllers;

import com.kolotilkin.users.models.User;
import com.kolotilkin.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class UsersController {

    @Autowired
    UsersRepository repository;

    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            if (repository.findByEmail(user.getEmail()) != null)
                throw new RuntimeException("User already exists");

            return new ResponseEntity<>(repository.save(user), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>("User was successfully deleted", HttpStatus.OK);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>("No such user", HttpStatus.NOT_FOUND);
        }
    }

}
