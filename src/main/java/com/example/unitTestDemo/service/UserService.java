package com.example.unitTestDemo.service;

import com.example.unitTestDemo.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserService() {
    }

    public boolean isAdult(User user) {
        return user.getAge() >= 18;
    }

    public String formatUserName(User user) {
        return user.getName().trim().toUpperCase();
    }

    public String getUserCategory(User user) {
        if (user.getAge() < 18) return "MINOR";
        if (user.getAge() < 60) return "ADULT";
        return "SENIOR";
    }
}
