package com.example.unitTestDemo.controller;

import com.example.unitTestDemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id) {
        return service.getUserName(id);
    }
}
