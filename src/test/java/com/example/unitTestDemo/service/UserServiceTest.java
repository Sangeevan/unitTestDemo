package com.example.unitTestDemo.service;

import com.example.unitTestDemo.model.User;
import com.example.unitTestDemo.repository.UserRepository;
import com.example.unitTestDemo.util.UserUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

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

    @Captor
    ArgumentCaptor<User> userCaptor;

    static User user1;
    static User user2;
    static List<User> allUsers;

    @BeforeEach
    void init() {
        user1 = new User(1L, "Sangeevan", 25);
        user2 = new User(2L, "Sangee", 65);
        allUsers = List.of(user1, user2);
    }

    @AfterEach
    void reset() {
        user1 = null;
        user2 = null;
        allUsers = null;
    }

    @Test
    void testIsAdult() {
        try (MockedStatic<UserUtils> mocked = mockStatic(UserUtils.class)) {
            mocked.when(() -> UserUtils.isAdult(user1)).thenReturn(true);

            assertTrue(service.isAdult(user1));
        }
    }

    @Test
    void testFormatUserName() {
        assertEquals("SANGEEVAN", service.formatUserName(user1));
    }

    @ParameterizedTest
    @MethodSource("userCategoryData")
    void testUserCategory(int age, String expected) {
        User user = new User(1L, "Test", age);

        assertEquals(expected, service.getUserCategory(user));
    }

    // Separate method for test data
    static Stream<Arguments> userCategoryData() {
        return Stream.of(
                Arguments.of(10, "MINOR"),
                Arguments.of(17, "MINOR"),
                Arguments.of(18, "ADULT"),
                Arguments.of(30, "ADULT"),
                Arguments.of(60, "SENIOR"),
                Arguments.of(80, "SENIOR")
        );
    }

    @Test
    void testGetUserName() {
        when(repo.getReferenceById(1L)).thenReturn(user1);
        String result = service.getUserName(1L);

        assertEquals("Sangeevan", result);
    }

    @Test
    void testSaveUser() {
        when(repo.save(any())).thenReturn(user1);

        User result = service.saveUser(user1);

        assertEquals("Sangeevan", result.getName());
        assertSame(user1, result);

        // **capture the argument passed to repo.save**
        verify(repo).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        // assert on captured object
        assertEquals(1L, capturedUser.getId());
        assertEquals("Sangeevan", capturedUser.getName());
        assertEquals(25, capturedUser.getAge());
    }

    @Test
    void testGetAllUsers() {
        when(repo.findAll()).thenReturn(allUsers);
        List<User> resultUsers = service.getAllUsers();

        assertAll(
                () -> assertEquals(2, resultUsers.size()),
                () -> assertIterableEquals(allUsers, resultUsers)
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
