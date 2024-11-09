package ir.selab.tdd.repository;

import ir.selab.tdd.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    private final Map<String, User> usersByUserName;
    private final Map<String, User> usersByEmail;

    public UserRepository(List<User> users) {
        this.usersByUserName = users.stream().collect(Collectors.toMap(User::getUsername, u -> u, (u1, u2) -> {
            throw new IllegalArgumentException("Two users can not have the same username");
        }));

        // TODO: implement (Some users may not have email!)
        this.usersByEmail = new HashMap<>();
    }

    public User getUserByUsername(String username) {
        return usersByUserName.get(username);
    }

    public User getUserByEmail(String email) {
        return usersByEmail.get(email);
    }

    public boolean addUser(User user) {
        if (usersByUserName.containsKey(user.getUsername())) {
            return false;
        }
        if (user.getEmail() != null && usersByEmail.containsKey(user.getEmail())) {
            return false;
        }
        usersByUserName.put(user.getUsername(), user);
        if (user.getEmail() != null) {
            usersByEmail.put(user.getEmail(), user);
        }
        return true;

    }

    public boolean removeUser(String username) {
        if (usersByUserName.containsKey(username)) {
            User user = usersByUserName.remove(username);
            if (user.getEmail() != null) {
                usersByEmail.remove(user.getEmail());
            }
            return true;
        }
        return false;
    }




    public int getUserCount() {
        return usersByUserName.size();
    }
}
