package com.example.unitTestDemo.service;

import com.example.unitTestDemo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserServiceTest {
    private final UserService service = new UserService();

    @Test
    void testIsAdult() {
        User user = new User(1L, "Sangeevan", 25);

        assertTrue(service.isAdult(user));
    }

    @Test
    void testFormatUserName() {
        User user = new User(1L, "  sangeevan  ", 25);

        assertEquals("SANGEEVAN", service.formatUserName(user));
    }

    @Test
    void testUserCategory() {
        User user = new User(1L, "Test", 65);

        assertEquals("SENIOR", service.getUserCategory(user));
    }
}
