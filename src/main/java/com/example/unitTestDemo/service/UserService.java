package com.example.unitTestDemo.service;

import com.example.unitTestDemo.model.User;
import com.example.unitTestDemo.repository.UserRepository;
import com.example.unitTestDemo.util.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public boolean isAdult(User user) {
        return UserUtils.isAdult(user);
    }

    public String formatUserName(User user) {
        return user.getName().trim().toUpperCase();
    }

    public String getUserCategory(User user) {
        if (user.getAge() < 18) return "MINOR";
        if (user.getAge() < 60) return "ADULT";
        return "SENIOR";
    }

    public String getUserName(Long id) {
        return repo.getReferenceById(id).getName();
    }

    public User saveUser(User user) {
        return repo.save(user);
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
