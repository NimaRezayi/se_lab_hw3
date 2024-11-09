package ir.selab.tdd;

import ir.selab.tdd.domain.User;
import ir.selab.tdd.repository.UserRepository;
import ir.selab.tdd.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepository userRepository = new UserRepository(List.of());
        userService = new UserService(userRepository);
        userService.registerUser("admin", "1234");
        userService.registerUser("ali", "qwert");
    }

    @Test
    public void createNewValidUser__ShouldSuccess() {
        String username = "reza";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertTrue(b);
    }

    @Test
    public void createNewDuplicateUser__ShouldFail() {
        String username = "ali";
        String password = "123abc";
        boolean b = userService.registerUser(username, password);
        assertFalse(b);
    }

    @Test
    public void loginWithValidUsernameAndPassword__ShouldSuccess() {
        boolean login = userService.loginWithUsername("admin", "1234");
        assertTrue(login);
    }

    @Test
    public void loginWithValidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("admin", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithInvalidUsernameAndInvalidPassword__ShouldFail() {
        boolean login = userService.loginWithUsername("ahmad", "abcd");
        assertFalse(login);
    }

    @Test
    public void loginWithValidEmailAndPassword__ShouldSuccess() {
        userService.registerUser("jane", "doe123", "jane@example.com");
        boolean login = userService.loginWithEmail("jane@example.com", "doe123");
        assertTrue(login);
    }

    @Test
    public void loginWithInvalidEmailAndPassword__ShouldFail() {
        userService.registerUser("john", "password123", "john@example.com");
        boolean login = userService.loginWithEmail("john@example.com", "wrongpassword");
        assertFalse(login);
    }

    @Test
    public void loginWithNonExistentEmail__ShouldFail() {
        boolean login = userService.loginWithEmail("nonexistent@example.com", "password123");
        assertFalse(login);
    }

    @Test
    public void changeUserEmail__ShouldUpdateEmail() {
        userService.registerUser("ali", "password", "ali@oldemail.com");
        boolean result = userService.changeUserEmail("ali", "ali@newemail.com");
        assertTrue(result);

        // Login with new email should succeed
        boolean login = userService.loginWithEmail("ali@newemail.com", "password");
        assertTrue(login);

        // Login with old email should fail
        login = userService.loginWithEmail("ali@oldemail.com", "password");
        assertFalse(login);
    }

    @Test
    public void changeEmailForNonExistentUser__ShouldFail() {
        boolean result = userService.changeUserEmail("nonexistentUser", "newemail@example.com");
        assertFalse(result);
    }
}
