package com.example.unitTestDemo.service;

import com.example.unitTestDemo.config.RabbitConfig;
import com.example.unitTestDemo.model.User;
import com.example.unitTestDemo.repository.UserRepository;
import com.example.unitTestDemo.util.UserUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    private final RestTemplate restTemplate;

    @Value("${user.service.base-url}")
    private String userServiceBaseUrl;


    public UserService(UserRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
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
        String url = userServiceBaseUrl + "/user/name/{id}";

        try {
            return restTemplate.getForObject(url, String.class, id);
        } catch (RestClientException ex) {
            System.err.println("Failed to call User Service: " + ex.getMessage());
            return "Unknown User";
        }
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

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receive(String name) {
        System.out.println("New User Received: " + name);
    }
}
