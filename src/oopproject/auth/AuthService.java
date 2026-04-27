package oopproject.auth;

import oopproject.exceptions.AuthenticationException;
import oopproject.storage.DataStorage;
import oopproject.users.User;

import java.util.Optional;

public class AuthService {
    private final DataStorage storage;

    public AuthService(DataStorage storage) {
        this.storage = storage;
    }

    public Optional<User> authenticate(String login, String password) {
        if (login == null || login.isBlank()) {
            throw new AuthenticationException(login, "login is blank");
        }
        if (password == null || password.isBlank()) {
            throw new AuthenticationException(login, "password is blank");
        }

        Optional<User> user = storage.getUsers().stream()
                .filter(candidate -> login.equals(candidate.getLogin()))
                .findFirst();

        if (user.isEmpty() || !password.equals(user.get().getPassword())) {
            throw new AuthenticationException(login, "invalid login or password");
        }

        storage.addLog(login, "authenticated");
        return user;
    }
}
