package oopproject.auth;

import oopproject.exceptions.AuthenticationException;
import oopproject.exceptions.AccessDeniedException;
import oopproject.storage.DataStorage;
import oopproject.system.UniversitySystem;
import oopproject.users.User;

import java.util.Optional;

public class AuthService {
    private final DataStorage storage;
    private final UniversitySystem system;
    private User currentUser;

    public AuthService(DataStorage storage) {
        this.storage = storage;
        this.system = null;
    }

    public AuthService(UniversitySystem system) {
        this.storage = null;
        this.system = system;
    }

    public Optional<User> authenticate(String login, String password) {
        return login(login, password);
    }

    public Optional<User> login(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new AuthenticationException(login, "login is blank");
        }
        if (password == null || password.isBlank()) {
            throw new AuthenticationException(login, "password is blank");
        }

        Optional<User> user = users().stream()
                .filter(candidate -> login.equals(candidate.getLogin()))
                .findFirst();

        if (user.isEmpty() || !password.equals(user.get().getPassword())) {
            throw new AuthenticationException(login, "invalid login or password");
        }

        currentUser = user.get();
        addLog(login, "authenticated");
        return user;
    }

    public void logout(User user) {
        if (user != null) {
            addLog(user.getLogin(), "logged out");
        }
        if (user != null && user.equals(currentUser)) {
            currentUser = null;
        }
    }

    public void checkAccess(User user) {
        if (user == null || currentUser == null || !user.equals(currentUser) || !user.login()) {
            throw new AccessDeniedException("User must be authenticated and active");
        }
    }

    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(currentUser);
    }

    private java.util.List<User> users() {
        return system == null ? storage.getUsers() : system.getUsers();
    }

    private void addLog(String username, String action) {
        if (system == null) {
            storage.addLog(username, action);
        } else {
            system.addLog(username, action);
        }
    }
}
