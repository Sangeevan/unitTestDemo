package com.example.unitTestDemo.service;

import com.example.unitTestDemo.model.User;
import com.example.unitTestDemo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository repo;

    @InjectMocks
    UserService service;

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

    @Test
    void testGetUserName() {
        when(repo.getReferenceById(1L)).thenReturn(new User(1L, "Sangeevan", 30));
        String result = service.getUserName(1L);

        assertEquals("Sangeevan", result);
    }

    @Test
    void testSaveUser() {
        User user = new User(1L, "Sangeevan", 30);
        when(repo.save(any())).thenReturn(user);
        User result = service.saveUser(user);

        assertEquals("Sangeevan", result.getName());

        assertSame(user, result);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(new User(1L, "Sangeevan", 30), new User(2L, "Sangee", 20));
        when(repo.findAll()).thenReturn(users);
        List<User> resultUsers = service.getAllUsers();

        assertAll(
                () -> assertEquals(2, resultUsers.size()),
                () -> assertIterableEquals(users, resultUsers)
        );
    }

    @Test
    void testUserNotFound() {
        when(repo.getReferenceById(2L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> {
            service.getUserName(2L);
        });
    }

    @Test
    void testDeleteUserCallsRepository() {
        Long userId = 1L;
        service.deleteUser(userId);

        // Verify the repo.deleteById was called exactly once with that id
        verify(repo, times(1)).deleteById(userId);
    }
}
